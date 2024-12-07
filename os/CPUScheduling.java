import java.util.*;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class CPUScheduling {
    static int totalBurstTime = 0;

    public static void main(String[] args) {
        Process[] processes = {
            new Process("P1", 0, 10),
            new Process("P2", 9, 6),
            new Process("P3", 17, 19),
            new Process("P4", 23, 13),
            new Process("P5", 41, 22)
        };

        // Calculate total burst time
        for (Process process : processes) {
            totalBurstTime += process.burstTime;
        }

        int firstPartTime = (int) (0.3 * totalBurstTime);
        int secondPartTime = (int) (0.4 * totalBurstTime);
        int thirdPartTime = totalBurstTime - firstPartTime - secondPartTime;

        System.out.println("Total Burst Time: " + totalBurstTime);
        System.out.println("First 30% time (FCFS): " + firstPartTime);
        System.out.println("Next 40% time (SJF): " + secondPartTime);
        System.out.println("Last 30% time (Round Robin): " + thirdPartTime);

        // Perform scheduling
        int currentTime = 0;
        int lastTime = 0;

        // FCFS scheduling
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        System.out.println("\nFCFS Scheduling:");
        currentTime = scheduleFCFS(processes, firstPartTime, currentTime);

        // SJF scheduling
        System.out.println("\nSJF Scheduling:");
        currentTime = scheduleSJF(processes, secondPartTime, currentTime);

        // Round Robin scheduling
        System.out.println("\nRound Robin Scheduling:");
        scheduleRoundRobin(processes, thirdPartTime, currentTime);

        // Calculate and print average turnaround time and waiting time
        printAverageTimes(processes);
    }

    private static int scheduleFCFS(Process[] processes, int timeLimit, int currentTime) {
        int totalTime = 0;
        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            if (totalTime < timeLimit) {
                currentTime += process.burstTime;
                process.completionTime = currentTime;
                process.turnaroundTime = process.completionTime - process.arrivalTime;
                process.waitingTime = process.turnaroundTime - process.burstTime;
                totalTime += process.burstTime;
                System.out.println(process.name + " - Completion Time: " + process.completionTime);
            }
        }
        return currentTime;
    }

    private static int scheduleSJF(Process[] processes, int timeLimit, int currentTime) {
        Arrays.sort(processes, Comparator.comparingInt(p -> p.burstTime));
        int totalTime = 0;
        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            if (totalTime < timeLimit) {
                currentTime += process.burstTime;
                process.completionTime = currentTime;
                process.turnaroundTime = process.completionTime - process.arrivalTime;
                process.waitingTime = process.turnaroundTime - process.burstTime;
                totalTime += process.burstTime;
                System.out.println(process.name + " - Completion Time: " + process.completionTime);
            }
        }
        return currentTime;
    }

    private static void scheduleRoundRobin(Process[] processes, int timeLimit, int currentTime) {
        int quantum = 4; // Time quantum for Round Robin
        Queue<Process> queue = new LinkedList<>();
        int totalTime = 0;

        while (totalTime < timeLimit) {
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && !queue.contains(process)) {
                    queue.add(process);
                }
            }

            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process currentProcess = queue.poll();
            if (currentProcess.burstTime > quantum) {
                currentTime += quantum;
                currentProcess.burstTime -= quantum;
                queue.add(currentProcess);
            } else {
                currentTime += currentProcess.burstTime;
                currentProcess.completionTime = currentTime;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - (currentProcess.burstTime + quantum);
                System.out.println(currentProcess.name + " - Completion Time: " + currentProcess.completionTime);
                totalTime += currentProcess.burstTime;
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
