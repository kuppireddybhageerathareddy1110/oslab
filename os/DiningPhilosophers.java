import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;
    private static final Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];

    public static void main(String[] args) {
        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];

        // Initialize semaphores for forks
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        // Create and start philosopher threads
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            int id = i; // Final variable for thread
            philosophers[i] = new Thread(new Philosopher(id));
            philosophers[i].start();
        }

        // Wait for threads to finish (they won't)
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Main thread interrupted");
            }
        }
    }

    // Philosopher class
    static class Philosopher implements Runnable {
        private final int id;

        Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // Thinking
                    System.out.println("Philosopher " + id + " is thinking.");
                    Thread.sleep((int) (Math.random() * 2000)); // Simulate thinking time

                    // Pick up forks
                    forks[id].acquire();
                    forks[(id + 1) % NUM_PHILOSOPHERS].acquire();

                    // Eating
                    System.out.println("Philosopher " + id + " is eating.");
                    Thread.sleep((int) (Math.random() * 2000)); // Simulate eating time

                    // Put down forks
                    forks[id].release();
                    forks[(id + 1) % NUM_PHILOSOPHERS].release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Philosopher " + id + " interrupted");
                }
            }
        }
    }
}