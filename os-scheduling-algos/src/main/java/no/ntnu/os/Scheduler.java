package no.ntnu.os;

import java.util.List;

/**
 * Common interface for all CPU scheduling algorithms.
 */
public interface Scheduler {
    String getName();

    /**
     * Schedules the given processes and returns them (or a copy) with
     * completionTime, turnaroundTime, and waitingTime filled in.
     */
    List<Process> schedule(List<Process> processes);
}
