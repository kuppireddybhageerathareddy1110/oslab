import java.util.Scanner;

public class MemoryAllocation {
    
    // Function to calculate total internal fragmentation using First Fit strategy
    public static int firstFit(int[] partitions, int[] processes) {
        int totalInternalFragmentation = 0;
        boolean[] partitionUsed = new boolean[partitions.length];
        
        for (int process : processes) {
            boolean allocated = false;
            for (int i = 0; i < partitions.length; i++) {
                if (!partitionUsed[i] && partitions[i] >= process) {
                    totalInternalFragmentation += partitions[i] - process;
                    partitionUsed[i] = true;
                    allocated = true;
                    break;
                }
            }
            if (!allocated) {
                System.out.println("Process of size " + process + " could not be allocated.");
            }
        }
        return totalInternalFragmentation;
    }

    // Function to calculate total internal fragmentation using Best Fit strategy
    public static int bestFit(int[] partitions, int[] processes) {
        int totalInternalFragmentation = 0;
        boolean[] partitionUsed = new boolean[partitions.length];
        
        for (int process : processes) {
            int bestIndex = -1;
            int minFrag = Integer.MAX_VALUE;
            for (int i = 0; i < partitions.length; i++) {
                int frag = partitions[i] - process;
                if (!partitionUsed[i] && partitions[i] >= process && frag < minFrag) {
                    minFrag = frag;
                    bestIndex = i;
                }
            }
            if (bestIndex != -1) {
                totalInternalFragmentation += partitions[bestIndex] - process;
                partitionUsed[bestIndex] = true;
            } else {
                System.out.println("Process of size " + process + " could not be allocated.");
            }
        }
        return totalInternalFragmentation;
    }

    // Function to calculate total internal fragmentation using Worst Fit strategy
    public static int worstFit(int[] partitions, int[] processes) {
        int totalInternalFragmentation = 0;
        boolean[] partitionUsed = new boolean[partitions.length];
        
        for (int process : processes) {
            int worstIndex = -1;
            int maxFrag = -1;
            for (int i = 0; i < partitions.length; i++) {
                int frag = partitions[i] - process;
                if (!partitionUsed[i] && partitions[i] >= process && frag > maxFrag) {
                    maxFrag = frag;
                    worstIndex = i;
                }
            }
            if (worstIndex != -1) {
                totalInternalFragmentation += partitions[worstIndex] - process;
                partitionUsed[worstIndex] = true;
            } else {
                System.out.println("Process of size " + process + " could not be allocated.");
            }
        }
        return totalInternalFragmentation;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input partition sizes
        System.out.print("Enter the number of partitions: ");
        int numPartitions = scanner.nextInt();
        int[] partitions = new int[numPartitions];
        System.out.println("Enter the sizes of the partitions:");
        for (int i = 0; i < numPartitions; i++) {
            partitions[i] = scanner.nextInt();
        }

        // Input process sizes
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        int[] processes = new int[numProcesses];
        System.out.println("Enter the sizes of the processes:");
        for (int i = 0; i < numProcesses; i++) {
            processes[i] = scanner.nextInt();
        }

        // Calculate and print total internal fragmentation for each strategy
        int firstFitFragmentation = firstFit(partitions.clone(), processes);
        int bestFitFragmentation = bestFit(partitions.clone(), processes);
        int worstFitFragmentation = worstFit(partitions.clone(), processes);

        System.out.println("Total Internal Fragmentation:");
        System.out.println("First Fit: " + firstFitFragmentation);
        System.out.println("Best Fit: " + bestFitFragmentation);
        System.out.println("Worst Fit: " + worstFitFragmentation);

        scanner.close();
    }
}
