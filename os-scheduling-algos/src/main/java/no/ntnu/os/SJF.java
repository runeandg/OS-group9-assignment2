package no.ntnu.os;

import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Job First (SJF) – Non-preemptive.
 *
 * Each time the CPU becomes free, the algorithm picks the arrived process with
 * the smallest burst time and runs it to completion without interruption.
 * Ties in burst time are broken by arrival time (earlier arrival wins), then
 * by the original input order.
 */
public class SJF implements Scheduler {

    @Override
    public String getName() {
        return "SJF (Shortest Job First)";
    }

    @Override
    public List<Process> schedule(List<Process> input) {
        List<Process> remaining = new ArrayList<>();
        for (Process p : input) {
            remaining.add(p.copy());
        }

        List<Process> result = new ArrayList<>();
        int currentTime = 0;

        while (!remaining.isEmpty()) {
            // Collect all processes that have arrived by currentTime
            List<Process> ready = new ArrayList<>();
            for (Process p : remaining) {
                if (p.arrivalTime <= currentTime) {
                    ready.add(p);
                }
            }

            if (ready.isEmpty()) {
                // CPU is idle – jump to the next arrival
                int nextArrival = Integer.MAX_VALUE;
                for (Process p : remaining) {
                    if (p.arrivalTime < nextArrival) {
                        nextArrival = p.arrivalTime;
                    }
                }
                currentTime = nextArrival;
                continue;
            }

            // Pick the process with the shortest burst; break ties by arrival time
            Process shortest = ready.get(0);
            for (Process p : ready) {
                if (p.burstTime < shortest.burstTime ||
                        (p.burstTime == shortest.burstTime && p.arrivalTime < shortest.arrivalTime)) {
                    shortest = p;
                }
            }

            remaining.remove(shortest);
            currentTime += shortest.burstTime;
            shortest.completionTime = currentTime;
            shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
            shortest.waitingTime    = shortest.turnaroundTime - shortest.burstTime;
            result.add(shortest);
        }
        return result;
    }
}
