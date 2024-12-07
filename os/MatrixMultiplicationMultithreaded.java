class MatrixMultiplicationThread extends Thread {
    private int[][] A;
    private int[][] B;
    private int[][] result;
    private int row;

    public MatrixMultiplicationThread(int[][] A, int[][] B, int[][] result, int row) {
        this.A = A;
        this.B = B;
        this.result = result;
        this.row = row;
    }

    @Override
    public void run() {
        int size = A.length;
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                result[row][j] += A[row][k] * B[k][j];
            }
        }
    }
}

public class MatrixMultiplicationMultithreaded {
    public static void main(String[] args) {
        int size = 4096;
        int[][] matrixA = new int[size][size];
        int[][] matrixB = new int[size][size];
        int[][] result = new int[size][size];

        // Initialize matrices with random values
        initializeMatrix(matrixA);
        initializeMatrix(matrixB);

        long startTime = System.nanoTime();

        // Create and start threads
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new MatrixMultiplicationThread(matrixA, matrixB, result, i);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < size; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Multithreaded Time: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    private static void initializeMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = (int) (Math.random() * 10);
            }
        }
    }
}
