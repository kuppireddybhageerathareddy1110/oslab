import java.util.Scanner;

public class RoundRobin {

    // Function to find the waiting time for all processes
    static void findWaitingTime(int processes[], int n, int bt[], int wt[], int quantum) {
        int rem_bt[] = new int[n];
        for (int i = 0; i < n; i++)
            rem_bt[i] = bt[i];
        
        int t = 0; // Current time

        // Continue loop until all processes are done
        while (true) {
            boolean done = true;

            for (int i = 0; i < n; i++) {
                if (rem_bt[i] > 0) {
                    done = false; // There is a pending process

                    if (rem_bt[i] > quantum) {
                        t += quantum;
                        rem_bt[i] -= quantum;
                    } else {
                        t += rem_bt[i];
                        wt[i] = t - bt[i];
                        rem_bt[i] = 0;
                    }
                }
            }

            // If all processes are done
            if (done == true)
                break;
        }
    }

    // Function to calculate turnaround time
    static void findTurnAroundTime(int processes[], int n, int bt[], int wt[], int tat[]) {
        for (int i = 0; i < n; i++)
            tat[i] = bt[i] + wt[i];
    }

    // Function to calculate average time
    static void findavgTime(int processes[], int n, int bt[], int quantum) {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0, total_tat = 0;

        findWaitingTime(processes, n, bt, wt, quantum);
        findTurnAroundTime(processes, n, bt, wt, tat);

        System.out.println("Processes\tBurst Time\tWaiting Time\tTurn Around Time");

        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(" " + (i + 1) + "\t\t" + bt[i] + "\t\t" + wt[i] + "\t\t " + tat[i]);
        }

        System.out.println("Average waiting time = " + (float) total_wt / (float) n);
        System.out.println("Average turn around time = " + (float) total_tat / (float) n);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int processes[] = new int[n];
        int burst_time[] = new int[n];

        // Input burst times for each process
        for (int i = 0; i < n; i++) {
            processes[i] = i + 1;
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burst_time[i] = sc.nextInt();
        }

        // Input quantum time
        System.out.print("Enter quantum time: ");
        int quantum = sc.nextInt();

        // Find average times
        findavgTime(processes, n, burst_time, quantum);

        sc.close();
    }
}
