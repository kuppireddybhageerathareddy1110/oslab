import java.util.*;

public class MemoryManagementPaging {

    // Constants for the number of pages and frames
    private static final int NUM_PAGES = 5; // Maximum pages can be set, but user will input which pages to access
    private static final int NUM_FRAMES = 3; // Maximum number of frames

    // Page table to store the frame number for each page
    private static int[] pageTable = new int[NUM_PAGES];
    private static boolean[] frames = new boolean[NUM_FRAMES]; // To track if frame is occupied

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize the page table with -1 (indicating no frame assigned)
        Arrays.fill(pageTable, -1);
        
        // Asking user for the number of pages to access
        System.out.print("Enter the number of page accesses: ");
        int numAccesses = sc.nextInt();

        // Asking user for the page access sequence
        int[] pagesToAccess = new int[numAccesses];
        System.out.println("Enter the page access sequence (0 to " + (NUM_PAGES - 1) + "): ");
        for (int i = 0; i < numAccesses; i++) {
            pagesToAccess[i] = sc.nextInt();
            if (pagesToAccess[i] < 0 || pagesToAccess[i] >= NUM_PAGES) {
                System.out.println("Invalid page number! Please enter a number between 0 and " + (NUM_PAGES - 1));
                i--; // Decrement i to repeat this entry
            }
        }

        // Access the pages and manage the frames
        for (int page : pagesToAccess) {
            System.out.println("Accessing page: " + page);
            handlePageAccess(page);
        }

        sc.close();
    }

    // Method to handle page access
    public static void handlePageAccess(int page) {
        // Check if the page is already in memory (page table)
        if (pageTable[page] != -1) {
            System.out.println("Page " + page + " is already in memory (Frame " + pageTable[page] + ").");
        } else {
            // Page fault: Page is not in memory, load it into a frame
            System.out.println("Page " + page + " not in memory. Page fault occurred!");
            loadPageIntoFrame(page);
        }
    }

    // Method to load a page into memory
    public static void loadPageIntoFrame(int page) {
        // Find an empty frame (not occupied)
        int frame = findEmptyFrame();
        if (frame == -1) {
            // No empty frames, replace a page using FIFO (First-In-First-Out) policy
            frame = replacePage();
        }

        // Load the page into the frame
        pageTable[page] = frame;
        frames[frame] = true; // Mark the frame as occupied
        System.out.println("Loaded page " + page + " into frame " + frame);
    }

    // Method to find an empty frame in memory
    public static int findEmptyFrame() {
        for (int i = 0; i < NUM_FRAMES; i++) {
            if (!frames[i]) {
                return i; // Empty frame found
            }
        }
        return -1; // No empty frames
    }

    // Method to replace a page using FIFO policy
    public static int replacePage() {
        // FIFO: Replace the first page (frame 0) for simplicity
        int frameToReplace = 0;
        System.out.println("Replacing page in frame " + frameToReplace);
        return frameToReplace;
    }
}
