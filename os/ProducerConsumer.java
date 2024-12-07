import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    private static final int BUFFER_SIZE = 5;
    private static int[] buffer = new int[BUFFER_SIZE];
    private static int in = 0, out = 0;

    // Semaphores for synchronization
    private static Semaphore mutex = new Semaphore(1); // For mutual exclusion
    private static Semaphore empty = new Semaphore(BUFFER_SIZE); // Tracks empty slots
    private static Semaphore full = new Semaphore(0); // Tracks full slots

    public static void main(String[] args) {
        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        producerThread.start();
        consumerThread.start();

        // Wait for threads to finish (not applicable here as they run indefinitely)
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted");
        }
    }

    // Producer class
    static class Producer implements Runnable {
        private int item = 0;

        @Override
        public void run() {
            while (true) {
                try {
                    empty.acquire(); // Wait if no empty slots
                    mutex.acquire(); // Lock the buffer

                    // Produce an item
                    buffer[in] = item;
                    System.out.println("Produced: " + item);
                    in = (in + 1) % BUFFER_SIZE;
                    item++;

                    mutex.release(); // Unlock the buffer
                    full.release(); // Signal a full slot

                    Thread.sleep(1000); // Simulate production time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Producer interrupted");
                }
            }
        }
    }

    // Consumer class
    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    full.acquire(); // Wait if no full slots
                    mutex.acquire(); // Lock the buffer

                    // Consume an item
                    int item = buffer[out];
                    System.out.println("Consumed: " + item);
                    out = (out + 1) % BUFFER_SIZE;

                    mutex.release(); // Unlock the buffer
                    empty.release(); // Signal an empty slot

                    Thread.sleep(15); // Simulate consumption time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Consumer interrupted");
                }
            }
        }
    }
}