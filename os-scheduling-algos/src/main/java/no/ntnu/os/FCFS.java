package no.ntnu.os;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * First Come First Serve (FCFS) – Non-preemptive.
 *
 * Processes are served in the order they arrive. When two processes share the
 * same arrival time the one listed first in the input keeps its position
 * (stable sort). Once a process starts it runs until completion.
 */
public class FCFS implements Scheduler {

    @Override
    public String getName() {
        return "FCFS (First Come First Serve)";
    }

    @Override
    public List<Process> schedule(List<Process> input) {
        // Copy processes so we never mutate the original list
        List<Process> processes = new ArrayList<>();
        for (Process p : input) {
            processes.add(p.copy());
        }

        // Sort by arrival time; Java's sort is stable so equal arrivals keep input order
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        for (Process p : processes) {
            // If CPU is idle before this process arrives, jump forward
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            currentTime += p.burstTime;
            p.completionTime  = currentTime;
            p.turnaroundTime  = p.completionTime - p.arrivalTime;
            p.waitingTime     = p.turnaroundTime - p.burstTime;
        }
        return processes;
    }
}
