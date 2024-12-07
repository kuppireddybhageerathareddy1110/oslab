import java.util.Scanner;

class FCFS 
{
    static void findWaitingTime(int processes[], int n, int bt[], int wt[]) 
    {
        wt[0] = 0;
        for (int i = 1; i < n; i++) 
        {
            wt[i] = bt[i - 1] + wt[i - 1];
        }
    }

    static void findTurnAroundTime(int processes[], int n, int bt[], int wt[], int tat[]) 
    {
        for (int i = 0; i < n; i++) 
        {
            tat[i] = bt[i] + wt[i];
        }
    }

    static void findavgTime(int processes[], int n, int bt[]) 
    {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0, total_tat = 0;

        findWaitingTime(processes, n, bt, wt);
        findTurnAroundTime(processes, n, bt, wt, tat);

        System.out.printf("Processes Burst time Waiting" + " time Turn around time\n");

        for (int i = 0; i < n; i++) 
        {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            System.out.printf(" %d ", processes[i]);
            System.out.printf("     %d ", bt[i]);
            System.out.printf("     %d", wt[i]);
            System.out.printf("     %d\n", tat[i]);
        }

        float avg_wt = (float)total_wt / n;
        float avg_tat = (float)total_tat / n;
        System.out.printf("Average waiting time = %.2f", avg_wt);
        System.out.printf("\n");
        System.out.printf("Average turn around time = %.2f\n", avg_tat);
    }

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        // Input: Number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int processes[] = new int[n];
        int burst_time[] = new int[n];

        // Input: Process numbers and burst times
        for (int i = 0; i < n; i++) 
        {
            processes[i] = i + 1;
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burst_time[i] = sc.nextInt();
        }

        // Call to function that calculates average time
        findavgTime(processes, n, burst_time);
    }
}
