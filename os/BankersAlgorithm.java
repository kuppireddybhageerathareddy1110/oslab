import java.util.Scanner;

public class BankersAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int pno = sc.nextInt();

        System.out.print("Enter number of resources: ");
        int rno = sc.nextInt();

        int[][] allocated = new int[pno][rno];
        int[][] max = new int[pno][rno];
        int[][] need = new int[pno][rno];
        int[] avail = new int[rno];
        int[] work = new int[rno];
        boolean[] finished = new boolean[pno];

        // Input total available resources
        System.out.print("\nEnter total available units of each resource: ");
        for (int i = 0; i < rno; i++) {
            avail[i] = sc.nextInt();
        }

        // Input maximum resources needed for each process
        System.out.println("\nEnter the maximum resources needed for each process:");
        for (int i = 0; i < pno; i++) {
            System.out.println("For process " + (i + 1) + ":");
            for (int j = 0; j < rno; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        // Input allocated resources for each process
        System.out.println("\nEnter the allocated resources for each process:");
        for (int i = 0; i < pno; i++) {
            System.out.println("For process " + (i + 1) + ":");
            for (int j = 0; j < rno; j++) {
                allocated[i][j] = sc.nextInt();
            }
        }

        // Calculate the work array (initial available resources)
        for (int j = 0; j < rno; j++) {
            int total = 0;
            for (int i = 0; i < pno; i++) {
                total += allocated[i][j];
            }
            work[j] = avail[j] - total;
        }

        // Calculate the need matrix
        for (int i = 0; i < pno; i++) {
            for (int j = 0; j < rno; j++) {
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }

        System.out.println("\nAllocated Matrix | Maximum Matrix | Need Matrix");
        for (int i = 0; i < pno; i++) {
            for (int j = 0; j < rno; j++) {
                System.out.printf("%4d", allocated[i][j]);
            }
            System.out.print(" | ");
            for (int j = 0; j < rno; j++) {
                System.out.printf("%4d", max[i][j]);
            }
            System.out.print(" | ");
            for (int j = 0; j < rno; j++) {
                System.out.printf("%4d", need[i][j]);
            }
            System.out.println();
        }

        // Banker's Algorithm to check safe state
        int count = 0;
        while (count < pno) {
            boolean allocatedToProcess = false;

            for (int i = 0; i < pno; i++) {
                if (!finished[i]) {
                    boolean canAllocate = true;

                    for (int j = 0; j < rno; j++) {
                        if (need[i][j] > work[j]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    if (canAllocate) {
                        System.out.println("\nProcess " + (i + 1) + " completed");
                        for (int j = 0; j < rno; j++) {
                            work[j] += allocated[i][j];
                        }
                        finished[i] = true;
                        count++;
                        allocatedToProcess = true;

                        System.out.print("Available resources: ");
                        for (int j = 0; j < rno; j++) {
                            System.out.print(work[j] + " ");
                        }
                        System.out.println();
                    }
                }
            }

            if (!allocatedToProcess) {
                break;
            }
        }

        if (count == pno) {
            System.out.println("\nThe system is in a safe state!");
        } else {
            System.out.println("\nThe system is in an unsafe state!");
        }

        sc.close();
    }
}