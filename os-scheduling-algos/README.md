# CPU Scheduling Simulator

A Java implementation of three CPU scheduling algorithms:

- **FCFS** – First Come First Serve (Non-preemptive)
- **SJF** – Shortest Job First (Non-preemptive)
- **SRTF** – Shortest Remaining Time First (Preemptive)

## Prerequisites

- Java 8 or higher
- Maven

## How to Run

### 1. Clone / navigate to the project

```bash
cd os-scheduling-algos
```

### 2. Compile

```bash
mvn compile
```

### 3. Run

```bash
mvn exec:java -Dexec.mainClass=no.ntnu.os.App
```

You will be presented with a menu:

```
╔══════════════════════════════════════╗
║     CPU Scheduling Simulator         ║
╚══════════════════════════════════════╝
1. Enter custom processes
2. Run predefined test cases (Cases 1–3)
Choice:
```

---

## Option 1 – Custom Input

Enter the number of processes, then each process on its own line in the format:

```
<ProcessID> <ArrivalTime> <BurstTime>
```

**Example session:**

```
Choice: 1
Number of processes: 3
Enter each process as:  <ProcessID> <ArrivalTime> <BurstTime>
P1 0 6
P2 2 4
P3 4 2
```

The simulator will run all three algorithms on your input and print a results table for each.

---

## Option 2 – Predefined Test Cases

Runs the three assignment test cases automatically:

| Case | Description |
|------|-------------|
| Case 1 | All 5 processes arrive at time 0 |
| Case 2 | One long process (burst=20) followed by short arrivals |
| Case 3 | One long process with continuous short arrivals (starvation demo) |

Results for each algorithm are printed per case, followed by a summary comparison table:

```
  Case        Algorithm                              Avg WT  Avg TAT
  ----------------------------------------------------------
  Case 1      FCFS (First Come First Serve)           10.80    15.40
              SJF (Shortest Job First)                 6.20    10.80
              SRTF (Shortest Remaining Time First)     6.20    10.80
  ...
```

---

## Output Fields

| Field | Formula |
|-------|---------|
| CT – Completion Time | Time when the process finishes |
| TAT – Turnaround Time | CT − Arrival Time |
| WT – Waiting Time | TAT − Burst Time |
