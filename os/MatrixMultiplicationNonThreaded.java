public class MatrixMultiplicationNonThreaded {
    public static void main(String[] args) {
        int size = 4096;
        int[][] matrixA = new int[size][size];
        int[][] matrixB = new int[size][size];
        int[][] result = new int[size][size];

        // Initialize matrices with random values
        initializeMatrix(matrixA);
        initializeMatrix(matrixB);

        long startTime = System.nanoTime();

        // Perform multiplication
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Non-Multithreaded Time: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    private static void initializeMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = (int) (Math.random() * 10);
            }
        }
    }
}
