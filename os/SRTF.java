class Process {
    int pid;  // Process ID
    int bt;   // Burst Time
    int art;  // Arrival Time

    public Process(int pid, int bt, int art) {
        this.pid = pid;
        this.bt = bt;
        this.art = art;
    }
}

public class SRTF {

    // Function to find the waiting time for all processes
    static void findWaitingTime(Process proc[], int n, int wt[]) {
        int rt[] = new int[n]; // Remaining times

        // Copy the burst time into rt[]
        for (int i = 0; i < n; i++) {
            rt[i] = proc[i].bt;
        }

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = -1, finish_time;
        boolean check = false;

        // Process until all processes get completed
        while (complete != n) {
            // Find the process with minimum remaining time that has arrived
            for (int j = 0; j < n; j++) {
                if ((proc[j].art <= t) && (rt[j] < minm) && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }

            if (!check) {
                t++;
                continue;
            }

            // Reduce remaining time by one
            rt[shortest]--;

            // Update minimum remaining time
            minm = rt[shortest];
            if (minm == 0) {
                minm = Integer.MAX_VALUE;
            }

            // If a process gets completely executed
            if (rt[shortest] == 0) {
                complete++;
                check = false;

                // Find finish time of the current process
                finish_time = t + 1;

                // Calculate waiting time
                wt[shortest] = finish_time - proc[shortest].bt - proc[shortest].art;

                if (wt[shortest] < 0) {
                    wt[shortest] = 0;
                }
            }

            // Increment time
            t++;
        }
    }

    // Function to calculate turn around time
    static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = proc[i].bt + wt[i];
        }
    }

    // Function to calculate average waiting and turn-around time
    static void findavgTime(Process proc[], int n) {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0, total_tat = 0;

        // Find waiting time for all processes
        findWaitingTime(proc, n, wt);

        // Find turn around time for all processes
        findTurnAroundTime(proc, n, wt, tat);

        // Display processes along with all details
        System.out.println("Processes\tBurst Time\tWaiting Time\tTurn Around Time");

        // Calculate total waiting time and total turn around time
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(proc[i].pid + "\t\t" + proc[i].bt + "\t\t" + wt[i] + "\t\t" + tat[i]);
        }

        System.out.println("Average waiting time = " + (float) total_wt / n);
        System.out.println("Average turn around time = " + (float) total_tat / n);
    }

    public static void main(String[] args) {
        Process proc[] = { 
            new Process(1, 6, 2),  // Process 1 with burst time 6 and arrival time 2
            new Process(2, 3, 1),  // Process 2 with burst time 3 and arrival time 1
            new Process(3, 8, 4),  // Process 3 with burst time 8 and arrival time 4
            new Process(4, 5, 5),  // Process 4 with burst time 5 and arrival time 5
            new Process(5, 4, 5)   // Process 5 with burst time 4 and arrival time 3
        };

        findavgTime(proc, proc.length);
    }
}
