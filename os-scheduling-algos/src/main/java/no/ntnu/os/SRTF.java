package no.ntnu.os;

import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Remaining Time First (SRTF) – Preemptive.
 *
 * At every clock tick the scheduler picks the arrived process with the least
 * remaining burst time. If a new arrival has a shorter remaining time than the
 * currently running process, it preempts it immediately.
 * Ties in remaining time are broken by arrival time (earlier arrival wins).
 */
public class SRTF implements Scheduler {

    @Override
    public String getName() {
        return "SRTF (Shortest Remaining Time First)";
    }

    @Override
    public List<Process> schedule(List<Process> input) {
        List<Process> processes = new ArrayList<>();
        for (Process p : input) {
            processes.add(p.copy());
        }

        int n = processes.size();
        int[] remaining = new int[n];
        for (int i = 0; i < n; i++) {
            remaining[i] = processes.get(i).burstTime;
        }

        int currentTime = 0;
        int completed = 0;

        while (completed < n) {
            // Find the arrived process with the shortest remaining time
            int selected = -1;
            for (int i = 0; i < n; i++) {
                if (processes.get(i).arrivalTime > currentTime || remaining[i] == 0) {
                    continue;
                }
                if (selected == -1) {
                    selected = i;
                } else if (remaining[i] < remaining[selected]) {
                    selected = i;
                } else if (remaining[i] == remaining[selected]
                        && processes.get(i).arrivalTime < processes.get(selected).arrivalTime) {
                    // Tie-break: prefer the process that arrived earlier
                    selected = i;
                }
            }

            if (selected == -1) {
                // No process has arrived yet – advance time by one tick
                currentTime++;
                continue;
            }

            // Run the selected process for one tick
            remaining[selected]--;
            currentTime++;

            if (remaining[selected] == 0) {
                // Process has finished
                completed++;
                Process p = processes.get(selected);
                p.completionTime = currentTime;
                p.turnaroundTime = p.completionTime - p.arrivalTime;
                p.waitingTime    = p.turnaroundTime - p.burstTime;
            }
        }
        return processes;
    }
}
