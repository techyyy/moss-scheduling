public class Process {
  public int cputime;
  public int ioblocking;
  public int cpudone;
  public int ionext;
  public int numblocked;
  public int lotteryTicketsCount;
  public int blockTime;
  public int processNumber;

  public Process(int cputime, int ioblocking, int cpudone, int ionext, int numblocked, int blockTime,
                 int lotteryTicketsCount, int processNumber) {
    this.cputime = cputime;
    this.ioblocking = ioblocking;
    this.cpudone = cpudone;
    this.ionext = ionext;
    this.numblocked = numblocked;
    this.blockTime = blockTime;
    this.lotteryTicketsCount = lotteryTicketsCount;
    this.processNumber = processNumber;
  }
}