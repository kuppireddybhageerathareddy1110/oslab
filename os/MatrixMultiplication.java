import java.util.Scanner;

public class MatrixMultiplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input for matrix A
        System.out.print("Enter the number of rows for matrix A: ");
        int rowsA = scanner.nextInt();
        System.out.print("Enter the number of columns for matrix A: ");
        int colsA = scanner.nextInt();
        
        int[][] A = new int[rowsA][colsA];
        System.out.println("Enter the elements of matrix A:");
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                A[i][j] = scanner.nextInt();
            }
        }

        // Input for matrix B
        System.out.print("Enter the number of columns for matrix B: ");
        int colsB = scanner.nextInt();
        
        int[][] B = new int[colsA][colsB]; // Number of rows in B is equal to colsA
        System.out.println("Enter the elements of matrix B:");
        for (int i = 0; i < colsA; i++) {
            for (int j = 0; j < colsB; j++) {
                B[i][j] = scanner.nextInt();
            }
        }

        // Result matrix C
        int[][] C = new int[rowsA][colsB];

        // Number of threads to use
        int numThreads = 2;
        Thread[] threads = new Thread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            final int startRow = i * (rowsA / numThreads);
            final int endRow = (i == numThreads - 1) ? rowsA : (i + 1) * (rowsA / numThreads);
            
            threads[i] = new Thread(() -> {
                for (int row = startRow; row < endRow; row++) {
                    for (int col = 0; col < colsB; col++) {
                        C[row][col] = 0;
                        for (int k = 0; k < colsA; k++) {
                            C[row][col] += A[row][k] * B[k][col];
                        }
                    }
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        // Print result matrix C
        System.out.println("Result matrix C:");
        for (int[] row : C) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
