import java.util.*;

class Process {
    String name;
    int priority;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(String name, int priority, int arrivalTime, int burstTime) {
        this.name = name;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Process[] processes = {
            new Process("P1", 1, 0, 10),
            new Process("P2", 3, 9, 6),
            new Process("P3", 4, 17, 19),
            new Process("P4", 2, 23, 13),
            new Process("P5", 1, 41, 22)
        };

        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        schedulePriority(processes);
        printAverageTimes(processes);
    }

    private static void schedulePriority(Process[] processes) {
        int currentTime = 0;
        boolean[] completed = new boolean[processes.length];
        int completedCount = 0;

        while (completedCount < processes.length) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            // Find the process with the highest priority that has arrived and is not completed
            for (int i = 0; i < processes.length; i++) {
                if (processes[i].arrivalTime <= currentTime && !completed[i]) {
                    if (processes[i].priority < highestPriority) {
                        highestPriority = processes[i].priority;
                        idx = i;
                    }
                }
            }

            if (idx != -1) {
                currentTime += processes[idx].burstTime;
                processes[idx].completionTime = currentTime;
                processes[idx].turnaroundTime = processes[idx].completionTime - processes[idx].arrivalTime;
                processes[idx].waitingTime = processes[idx].turnaroundTime - processes[idx].burstTime;
                completed[idx] = true;
                completedCount++;
                System.out.println(processes[idx].name + " - Completion Time: " + processes[idx].completionTime);
            } else {
                // If no process is ready, increment current time
                currentTime++;
            }
        }
    }

    private static void printAverageTimes(Process[] processes) {
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;
        }
        System.out.println("\nAverage Turnaround Time: " + (totalTurnaroundTime / processes.length));
        System.out.println("Average Waiting Time: " + (totalWaitingTime / processes.length));
    }
}
