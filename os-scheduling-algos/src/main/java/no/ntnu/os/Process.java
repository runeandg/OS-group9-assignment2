package no.ntnu.os;

/**
 * Represents a single process with scheduling input and computed result fields.
 */
public class Process {
    public final String pid;
    public final int arrivalTime;
    public final int burstTime;

    // Computed by the scheduler
    public int completionTime;
    public int turnaroundTime;
    public int waitingTime;

    public Process(String pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    /** Returns a fresh copy so each scheduler run starts from clean state. */
    public Process copy() {
        return new Process(pid, arrivalTime, burstTime);
    }

    public void derive(int completedTime) {
        this.completionTime = completedTime;
        this.turnaroundTime = completionTime - arrivalTime;
        this.waitingTime = turnaroundTime - burstTime;
    }
}
