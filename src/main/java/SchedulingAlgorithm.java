// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.*;
import java.io.*;

public class SchedulingAlgorithm {

    public static Results Run(int runtime, Vector<Process> processVector, Results result) {
        int comptime = 0;
        int size = processVector.size();
        int completed = 0;
        int ticketsCount = 0;
        Map<Integer, Integer> blockedProcesses = new HashMap<>();

        String resultsFile = "Summary-Processes";

        result.schedulingType = "Interactive (Preemptive)";
        result.schedulingName = "Lottery scheduling";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            Vector<Process> processes = (Vector<Process>) processVector.clone();

            for (int i = 0; i < size; i++) ticketsCount += processes.elementAt(i).lotteryTicketsCount;

            LotteryScheduler lotteryScheduler = new LotteryScheduler(ticketsCount);
            int processIndex = lotteryScheduler.schedule(processes);
            Process process = processes.get(processIndex);


            out.println("Process: " + process.processNumber + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
            process.cpudone++;
            comptime++;

            while (comptime < runtime) {
                if (process.cpudone >= process.cputime) {
                    completed++;
                    out.println("Process: " + process.processNumber + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");

                    if (completed == size) {
                        result.compuTime = comptime;
                        out.close();
                        return result;
                    }

                    lotteryScheduler.setTicketsCount(lotteryScheduler.getTicketsCount() - process.lotteryTicketsCount);
                    processes.remove(process);
                    blockedProcesses.remove(process.processNumber);

                    if (processes.size() == 1) {
                        process = processes.elementAt(0);
                    }
                }

                if (process.ioblocking == process.ionext && blockedProcesses.size() < processes.size() - 1) {
                    out.println("Process: " + process.processNumber + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");

                    blockedProcesses.put(process.processNumber, comptime + process.blockTime);
                    process.ionext = 0;
                    process.numblocked++;
                }

                if (processes.size() > 1) {
                    processIndex = lotteryScheduler.schedule(processes);
                    process = processes.elementAt(processIndex);
                    out.println("Process: " + process.processNumber + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
                }

                Set<Integer> processesToUnblock = new HashSet<>();
                for (Integer processNumber : blockedProcesses.keySet()) {
                    if (blockedProcesses.get(processNumber) == comptime) {
                        processesToUnblock.add(processNumber);
                    }
                }

                for (Integer processNumber : processesToUnblock) {
                    blockedProcesses.remove(processNumber);
                    out.println("Process: " + processNumber + " I/O unblocked...");
                }

                process.cpudone++;
                process.ionext++;
                comptime++;
            }
            out.close();
        } catch (IOException e) {/* Handle exceptions */ }
        result.compuTime = comptime;
        return result;
    }
}