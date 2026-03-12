package no.ntnu.os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the CPU Scheduling Simulator.
 *
 * Usage:
 *   Option 1 – Custom input: enter your own processes interactively.
 *   Option 2 – Predefined test cases: runs Cases 1, 2, and 3 from the assignment.
 */
public class App {

    private static final List<Scheduler> SCHEDULERS = Arrays.asList(
            new FCFS(),
            new SJF(),
            new SRTF()
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     CPU Scheduling Simulator         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("1. Enter custom processes");
        System.out.println("2. Run predefined test cases (Cases 1–3)");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();

        if (choice == 1) {
            List<Process> processes = readProcesses(scanner);
            for (Scheduler scheduler : SCHEDULERS) {
                printResults(scheduler.getName(), scheduler.schedule(processes));
            }
        } else {
            runTestCases();
        }

        scanner.close();
    }

    // -------------------------------------------------------------------------
    // Input
    // -------------------------------------------------------------------------

    private static List<Process> readProcesses(Scanner scanner) {
        System.out.print("Number of processes: ");
        int n = scanner.nextInt();
        List<Process> processes = new ArrayList<>();
        System.out.println("Enter each process as:  <ProcessID> <ArrivalTime> <BurstTime>");
        for (int i = 0; i < n; i++) {
            String pid     = scanner.next();
            int arrival    = scanner.nextInt();
            int burst      = scanner.nextInt();
            processes.add(new Process(pid, arrival, burst));
        }
        return processes;
    }

    // -------------------------------------------------------------------------
    // Predefined test cases
    // -------------------------------------------------------------------------

    private static void runTestCases() {
        String[] caseNames = {
            "Case 1 – All Processes Arrive at Time 0",
            "Case 2 – One Long Process Followed by Short Processes",
            "Case 3 – Continuous Arrival of Short Processes"
        };

        List<List<Process>> cases = Arrays.asList(
            // Case 1
            Arrays.asList(
                new Process("P1", 0, 8),
                new Process("P2", 0, 4),
                new Process("P3", 0, 2),
                new Process("P4", 0, 6),
                new Process("P5", 0, 3)
            ),
            // Case 2
            Arrays.asList(
                new Process("P1", 0, 20),
                new Process("P2", 1,  2),
                new Process("P3", 2,  2),
                new Process("P4", 3,  1),
                new Process("P5", 4,  3)
            ),
            // Case 3
            Arrays.asList(
                new Process("P1", 0, 20),
                new Process("P2", 1,  2),
                new Process("P3", 2,  2),
                new Process("P4", 3,  2),
                new Process("P5", 4,  2),
                new Process("P6", 5,  2)
            )
        );

        // Collect summary data for the comparison table
        double[][] avgWT  = new double[3][3];
        double[][] avgTAT = new double[3][3];

        for (int c = 0; c < cases.size(); c++) {
            System.out.println("\n" + "=".repeat(62));
            System.out.println("  " + caseNames[c]);
            System.out.println("=".repeat(62));

            for (int s = 0; s < SCHEDULERS.size(); s++) {
                Scheduler scheduler = SCHEDULERS.get(s);
                List<Process> result = scheduler.schedule(cases.get(c));
                printResults(scheduler.getName(), result);

                double totalWT = 0, totalTAT = 0;
                for (Process p : result) {
                    totalWT  += p.waitingTime;
                    totalTAT += p.turnaroundTime;
                }
                avgWT[c][s]  = totalWT  / result.size();
                avgTAT[c][s] = totalTAT / result.size();
            }
        }

        // Print the overall comparison table
        printComparisonTable(caseNames, avgWT, avgTAT);
    }

    // -------------------------------------------------------------------------
    // Output helpers
    // -------------------------------------------------------------------------

    private static void printResults(String algorithmName, List<Process> results) {
        System.out.println("\n  -- " + algorithmName + " --");
        System.out.printf("  %-6s  %-9s  %-7s  %-6s  %-6s  %-6s%n",
                "PID", "Arrival", "Burst", "CT", "TAT", "WT");
        System.out.println("  " + "-".repeat(48));

        double totalWT = 0, totalTAT = 0;
        for (Process p : results) {
            System.out.printf("  %-6s  %-9d  %-7d  %-6d  %-6d  %-6d%n",
                    p.pid, p.arrivalTime, p.burstTime,
                    p.completionTime, p.turnaroundTime, p.waitingTime);
            totalWT  += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }
        System.out.println("  " + "-".repeat(48));
        System.out.printf("  %-36s  %-6.2f  %-6.2f%n",
                "Average", totalTAT / results.size(), totalWT / results.size());
    }

    private static void printComparisonTable(String[] caseNames,
            double[][] avgWT, double[][] avgTAT) {
        System.out.println("\n" + "=".repeat(62));
        System.out.println("  COMPARISON TABLE");
        System.out.println("=".repeat(62));
        System.out.printf("  %-10s  %-36s  %7s  %7s%n", "Case", "Algorithm", "Avg WT", "Avg TAT");
        System.out.println("  " + "-".repeat(58));

        for (int c = 0; c < caseNames.length; c++) {
            String caseLabel = "Case " + (c + 1);
            for (int s = 0; s < SCHEDULERS.size(); s++) {
                System.out.printf("  %-10s  %-36s  %7.2f  %7.2f%n",
                        s == 0 ? caseLabel : "",
                        SCHEDULERS.get(s).getName(),
                        avgWT[c][s],
                        avgTAT[c][s]);
            }
            if (c < caseNames.length - 1) {
                System.out.println("  " + "-".repeat(58));
            }
        }
        System.out.println("=".repeat(62));
    }
}
