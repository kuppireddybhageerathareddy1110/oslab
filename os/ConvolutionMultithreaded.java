class ConvolutionThread extends Thread {
    private int[][] matrix;
    private int[][] mask;
    private int[][] result;
    private int startRow;
    private int endRow;

    public ConvolutionThread(int[][] matrix, int[][] mask, int[][] result, int startRow, int endRow) {
        this.matrix = matrix;
        this.mask = mask;
        this.result = result;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    public void run() {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < matrix.length - 8; j++) {
                result[i][j] = applyMask(matrix, mask, i, j);
            }
        }
    }

    private int applyMask(int[][] matrix, int[][] mask, int row, int col) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sum += matrix[row + i][col + j] * mask[i][j];
            }
        }
        return sum;
    }
}

public class ConvolutionMultithreaded {
    public static void main(String[] args) {
        int size = 4096;
        int[][] matrix = new int[size][size];
        int[][] mask = new int[9][9]; // Example mask
        int[][] result = new int[size][size];

        // Initialize the matrix and mask with random values
        initializeMatrix(matrix);
        initializeMask(mask);

        long startTime = System.nanoTime();

        // Create and start threads
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];
        int rowsPerThread = size / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int startRow = i * rowsPerThread;
            int endRow = (i == numThreads - 1) ? size - 8 : startRow + rowsPerThread;
            threads[i] = new ConvolutionThread(matrix, mask, result, startRow, endRow);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Multithreaded Convolution Time: " + (endTime - startTime) / 1_000_000 + " ms");
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
