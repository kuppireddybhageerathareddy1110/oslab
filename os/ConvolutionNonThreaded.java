public class ConvolutionNonThreaded {
    public static void main(String[] args) {
        int size = 4096;
        int[][] matrix = new int[size][size];
        int[][] mask = new int[9][9]; // Example mask
        int[][] result = new int[size][size];

        // Initialize the matrix and mask with random values
        initializeMatrix(matrix);
        initializeMask(mask);

        long startTime = System.nanoTime();

        // Perform convolution
        for (int i = 0; i < size - 8; i++) {
            for (int j = 0; j < size - 8; j++) {
                result[i][j] = applyMask(matrix, mask, i, j);
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Non-Multithreaded Convolution Time: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    private static int applyMask(int[][] matrix, int[][] mask, int row, int col) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sum += matrix[row + i][col + j] * mask[i][j];
            }
        }
        return sum;
    }

    private static void initializeMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = (int) (Math.random() * 10);
            }
        }
    }

    private static void initializeMask(int[][] mask) {
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[i].length; j++) {
                mask[i][j] = (int) (Math.random() * 10);
            }
        }
    }
}
