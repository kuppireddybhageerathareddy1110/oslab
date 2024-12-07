import java.util.concurrent.Semaphore;

public class ReaderWriter {
    private static Semaphore mutex = new Semaphore(1); // To synchronize reader count
    private static Semaphore writeLock = new Semaphore(1); // To synchronize writer access
    private static int readersCount = 0;

    public static void main(String[] args) {
        // Create reader and writer threads
        Thread reader1 = new Thread(new Reader("Reader 1"));
        Thread reader2 = new Thread(new Reader("Reader 2"));
        Thread writer1 = new Thread(new Writer("Writer 1"));
        Thread writer2 = new Thread(new Writer("Writer 2"));

        reader1.start();
        reader2.start();
        writer1.start();
        writer2.start();

        // Wait for threads to finish (not applicable here as they run indefinitely)
        try {
            reader1.join();
            reader2.join();
            writer1.join();
            writer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted");
        }
    }

    // Reader class
    static class Reader implements Runnable {
        private String name;

        Reader(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    mutex.acquire(); // Lock for updating readersCount
                    readersCount++;
                    if (readersCount == 1) {
                        writeLock.acquire(); // First reader locks the writer
                    }
                    mutex.release();

                    // Reading section
                    System.out.println(name + " is reading");
                    Thread.sleep(1000); // Simulate reading time

                    mutex.acquire();
                    readersCount--;
                    if (readersCount == 0) {
                        writeLock.release(); // Last reader unlocks the writer
                    }
                    mutex.release();

                    Thread.sleep(1000); // Simulate time between reads
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(name + " interrupted");
                }
            }
        }
    }

    // Writer class
    static class Writer implements Runnable {
        private String name;

        Writer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    writeLock.acquire(); // Lock for writing

                    // Writing section
                    System.out.println(name + " is writing");
                    Thread.sleep(1000); // Simulate writing time

                    writeLock.release(); // Unlock for other writers or readers

                    Thread.sleep(1000); // Simulate time between writes
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(name + " interrupted");
                }
            }
        }
    }
}