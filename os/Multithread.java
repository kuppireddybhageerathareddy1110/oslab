class MultithreadingDemo extends Thread {
    @Override
    public void run() {
        try {
            // Printing the ID of the current thread
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
        } catch (Exception e) {
            // Handling potential exceptions
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}

public class Multithread {
    public static void main(String[] args) {
        int numberOfThreads = 7;  // Number of threads to be created

        for (int i = 0; i < numberOfThreads; i++) {
            MultithreadingDemo threadObject = new MultithreadingDemo();
            threadObject.start();  // Start the new thread
        }
    }
}
