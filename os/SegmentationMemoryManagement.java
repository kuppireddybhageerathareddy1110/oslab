import java.util.*;

class Segment {
    String name;
    int base;
    int limit;

    public Segment(String name, int base, int limit) {
        this.name = name;
        this.base = base;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Segment: " + name + ", Base: " + base + ", Limit: " + limit;
    }
}

public class SegmentationMemoryManagement {
    private List<Segment> memorySegments;
    private int totalMemorySize;
    private int allocatedMemorySize;

    public SegmentationMemoryManagement(int totalMemorySize) {
        this.totalMemorySize = totalMemorySize;
        this.allocatedMemorySize = 0;
        this.memorySegments = new ArrayList<>();
    }

    public boolean allocateSegment(String name, int segmentSize) {
        if (allocatedMemorySize + segmentSize > totalMemorySize) {
            System.out.println("Insufficient memory for segment: " + name);
            return false;
        }

        int baseAddress = allocatedMemorySize;
        memorySegments.add(new Segment(name, baseAddress, segmentSize));
        allocatedMemorySize += segmentSize;

        System.out.println("Allocated " + segmentSize + " units for segment " + name + " at base address " + baseAddress);
        return true;
    }

    public void deallocateSegment(String name) {
        for (int i = 0; i < memorySegments.size(); i++) {
            if (memorySegments.get(i).name.equals(name)) {
                allocatedMemorySize -= memorySegments.get(i).limit;
                System.out.println("Deallocated segment: " + name);
                memorySegments.remove(i);
                return;
            }
        }
        System.out.println("Segment " + name + " not found.");
    }

    public void displayMemoryLayout() {
        System.out.println("\nMemory Layout:");
        for (Segment segment : memorySegments) {
            System.out.println(segment);
        }
        System.out.println("Total allocated memory: " + allocatedMemorySize + " out of " + totalMemorySize);
        System.out.println("Internal Fragmentation: " + (totalMemorySize - allocatedMemorySize));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total memory size: ");
        int totalMemorySize = sc.nextInt();
        SegmentationMemoryManagement memory = new SegmentationMemoryManagement(totalMemorySize);

        while (true) {
            System.out.println("\n1. Allocate Segment");
            System.out.println("2. Deallocate Segment");
            System.out.println("3. Display Memory Layout");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter segment name: ");
                    String name = sc.next();
                    System.out.print("Enter segment size: ");
                    int size = sc.nextInt();
                    memory.allocateSegment(name, size);
                    break;
                case 2:
                    System.out.print("Enter segment name to deallocate: ");
                    String segmentName = sc.next();
                    memory.deallocateSegment(segmentName);
                    break;
                case 3:
                    memory.displayMemoryLayout();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
