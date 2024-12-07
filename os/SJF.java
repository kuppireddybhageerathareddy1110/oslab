import java.util.Scanner;

public class SJF {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n;
        int[][] A = new int[100][4]; // 2D array to store process info: process ID, burst time, waiting time, and TAT
        int total = 0; // Total waiting time
        float avg_wt, avg_tat; // Average waiting time and turnaround time

        System.out.println("Enter number of processes:");
        n = input.nextInt();

        System.out.println("Enter Burst Time:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + ": ");
            A[i][1] = input.nextInt(); // Store burst time
            A[i][0] = i + 1; // Store process ID
        }

        // Sorting processes by burst time using SJF (Shortest Job First)
        for (int i = 0; i < n; i++) {
            int index = i;
            for (int j = i + 1; j < n; j++) {
                if (A[j][1] < A[index][1]) {
                    index = j;
                }
            }

            // Swap burst time and process number
            int temp = A[i][1];
            A[i][1] = A[index][1];
            A[index][1] = temp;

            temp = A[i][0];
            A[i][0] = A[index][0];
            A[index][0] = temp;
        }

        // Calculate waiting time for each process
        A[0][2] = 0; // Waiting time for first process is always 0
        for (int i = 1; i < n; i++) {
            A[i][2] = 0;
            for (int j = 0; j < i; j++) {
                A[i][2] += A[j][1]; // Waiting time is the sum of burst times of previous processes
            }
            total += A[i][2]; // Accumulate total waiting time
        }

        // Calculate average waiting time
        avg_wt = (float) total / n;
        total = 0; // Reset total for turnaround time calculation

        // Display process details
        System.out.println("P\tBT\tWT\tTAT");
        for (int i = 0; i < n; i++) {
            A[i][3] = A[i][1] + A[i][2]; // Turnaround time = burst time + waiting time
            total += A[i][3]; // Accumulate total turnaround time
            System.out.println("P" + A[i][0] + "\t" + A[i][1] + "\t" + A[i][2] + "\t" + A[i][3]);
        }

        // Calculate average turnaround time
        avg_tat = (float) total / n;
        System.out.println("Average Waiting Time= " + avg_wt);
        System.out.println("Average Turnaround Time= " + avg_tat);
    }
}
