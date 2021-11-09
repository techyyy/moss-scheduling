import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Vector;

@AllArgsConstructor
@Getter
@Setter
public class LotteryScheduler {

    private int ticketsCount;

    public int schedule(Vector<Process> processVector) {
        int chosenLotteryTicketNumber = 1 + (int) (Math.random() * ticketsCount);
        int tickets = 0;
        for (int i = 0; i < processVector.size(); i++) {
            tickets += processVector.get(i).lotteryTicketsCount;
            if (chosenLotteryTicketNumber <= tickets && processVector.get(i).cpudone < processVector.get(i).cputime) {
                return i;
            }
        }
        return -1;
    }
}