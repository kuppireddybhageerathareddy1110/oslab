import java.util.*;

class Process {
    int pid;       // Process ID
    int bt;        // Burst Time
    int priority;  // Priority

    // Constructor for Process class
    Process(int pid, int bt, int priority) {
        this.pid = pid;
        this.bt = bt;
        this.priority = priority;
    }

    // Method to return the priority of a process
    public int prior() {
        return priority;
    }
}

public class Priority {
    
    // Function to calculate waiting time for all processes
    public void findWaitingTime(Process proc[], int n, int wt[]) {
        wt[0] = 0; // Waiting time for first process is 0
        for (int i = 1; i < n; i++) {
            wt[i] = proc[i - 1].bt + wt[i - 1];
        }
    }

    // Function to calculate turnaround time for all processes
    public void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = proc[i].bt + wt[i];
        }
    }

    // Function to calculate average waiting and turnaround times
    public void findavgTime(Process proc[], int n) {
        int wt[] = new int[n], tat[] = new int[n], total_wt = 0, total_tat = 0;

        // Calculate waiting time of all processes
        findWaitingTime(proc, n, wt);

        // Calculate turnaround time for all processes
        findTurnAroundTime(proc, n, wt, tat);

        System.out.println("\nProcesses\tBurst Time\tWaiting Time\tTurn Around Time");

        // Display processes along with their burst time, waiting time, and turnaround time
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(" " + proc[i].pid + "\t\t" + proc[i].bt + "\t\t" + wt[i] + "\t\t" + tat[i]);
        }

        // Calculate and display average waiting and turnaround times
        System.out.println("\nAverage waiting time = " + (float) total_wt / n);
        System.out.println("Average turn around time = " + (float) total_tat / n);
    }

    // Function to execute priority scheduling
    public void priorityScheduling(Process proc[], int n) {
        // Sort processes based on priority (higher priority comes first)
        Arrays.sort(proc, new Comparator<Process>() {
            @Override
            public int compare(Process a, Process b) {
                return b.prior() - a.prior();
            }
        });

        System.out.println("Order in which processes get executed: ");
        for (int i = 0; i < n; i++) {
            System.out.print(proc[i].pid + " ");
        }
        System.out.println();
        
        // Calculate average waiting and turnaround times
        findavgTime(proc, n);
    }

    public static void main(String[] args) {
        Priority ob = new Priority();

        // Accept input from user
        Scanner sc = new Scanner(System.in);

        // Input: number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process proc[] = new Process[n];

        // Input: process ID, burst time, and priority for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter process ID, burst time, and priority for process " + (i + 1) + ": ");
            int pid = sc.nextInt();
            int bt = sc.nextInt();
            int priority = sc.nextInt();
            proc[i] = new Process(pid, bt, priority);
        }

        // Execute priority scheduling
        ob.priorityScheduling(proc, n);
    }
}
