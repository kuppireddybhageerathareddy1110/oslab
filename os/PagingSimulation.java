import java.util.*;

public class PagingSimulation {
    private int numFrames;
    private List<Integer> frames;
    private int[] pageReferences;

    public PagingSimulation(int numFrames, int[] pageReferences) {
        this.numFrames = numFrames;
        this.frames = new ArrayList<>();
        this.pageReferences = pageReferences;
    }

    // FIFO Page Replacement
    public int fifo() {
        int pageFaults = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int page : pageReferences) {
            if (!frames.contains(page)) {
                if (frames.size() == numFrames) {
                    int removedPage = queue.poll();
                    frames.remove((Integer) removedPage);
                }
                frames.add(page);
                queue.add(page);
                pageFaults++;
            }
            System.out.println("FIFO Frames: " + frames);
        }
        return pageFaults;
    }

    // Optimal Page Replacement
    public int optimal() {
        int pageFaults = 0;
        for (int i = 0; i < pageReferences.length; i++) {
            int page = pageReferences[i];
            if (!frames.contains(page)) {
                if (frames.size() == numFrames) {
                    int pageToReplace = findOptimal(i);
                    frames.remove((Integer) pageToReplace);
                }
                frames.add(page);
                pageFaults++;
            }
            System.out.println("Optimal Frames: " + frames);
        }
        return pageFaults;
    }

    private int findOptimal(int currentIndex) {
        int farthestIndex = -1, pageToReplace = -1;
        for (int page : frames) {
            int nextUse = Integer.MAX_VALUE;
            for (int j = currentIndex + 1; j < pageReferences.length; j++) {
                if (pageReferences[j] == page) {
                    nextUse = j;
                    break;
                }
            }
            if (nextUse > farthestIndex) {
                farthestIndex = nextUse;
                pageToReplace = page;
            }
        }
        return pageToReplace;
    }

    // LRU Page Replacement
    public int lru() {
        int pageFaults = 0;
        LinkedHashMap<Integer, Integer> lruMap = new LinkedHashMap<>(numFrames, 0.75f, true);
        for (int page : pageReferences) {
            if (!frames.contains(page)) {
                if (frames.size() == numFrames) {
                    if (!lruMap.isEmpty()) {
                        int lruPage = lruMap.keySet().iterator().next();
                        frames.remove((Integer) lruPage);
                        lruMap.remove(lruPage);
                    }
                }
                frames.add(page);
                pageFaults++;
            }
            lruMap.put(page, page);
            System.out.println("LRU Frames: " + frames);
        }
        return pageFaults;
    }

    // LFU Page Replacement
    public int lfu() {
        int pageFaults = 0;
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int page : pageReferences) {
            frequencyMap.put(page, frequencyMap.getOrDefault(page, 0) + 1);
            if (!frames.contains(page)) {
                if (frames.size() == numFrames) {
                    int lfuPage = findLFU(frequencyMap);
                    frames.remove((Integer) lfuPage);
                    frequencyMap.remove(lfuPage);
                }
                frames.add(page);
                pageFaults++;
            }
            System.out.println("LFU Frames: " + frames);
        }
        return pageFaults;
    }

    private int findLFU(Map<Integer, Integer> frequencyMap) {
        return Collections.min(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    // MRU Page Replacement
    public int mru() {
        int pageFaults = 0;
        Stack<Integer> stack = new Stack<>();
        for (int page : pageReferences) {
            if (!frames.contains(page)) {
                if (frames.size() == numFrames) {
                    int mruPage = stack.pop();
                    frames.remove((Integer) mruPage);
                }
                frames.add(page);
                pageFaults++;
            }
            stack.push(page);
            System.out.println("MRU Frames: " + frames);
        }
        return pageFaults;
    }

    // Clock Page Replacement
    public int clock() {
        int pageFaults = 0;
        int[] referenceBits = new int[numFrames];
        int pointer = 0;
        for (int page : pageReferences) {
            if (!frames.contains(page)) {
                while (frames.size() == numFrames && referenceBits[pointer] == 1) {
                    referenceBits[pointer] = 0;
                    pointer = (pointer + 1) % numFrames;
                }
                if (frames.size() == numFrames) {
                    frames.set(pointer, page);
                } else {
                    frames.add(page);
                }
                referenceBits[pointer] = 1;
                pointer = (pointer + 1) % numFrames;
                pageFaults++;
            } else {
                referenceBits[frames.indexOf(page)] = 1;
            }
            System.out.println("Clock Frames: " + frames);
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of frames: ");
        int numFrames = sc.nextInt();
        System.out.print("Enter the number of page references: ");
        int numPageReferences = sc.nextInt();
        int[] pageReferences = new int[numPageReferences];
        System.out.println("Enter the page reference string:");
        for (int i = 0; i < numPageReferences; i++) {
            pageReferences[i] = sc.nextInt();
        }

        PagingSimulation pagingSim = new PagingSimulation(numFrames, pageReferences);

        System.out.println("FIFO Page Faults: " + pagingSim.fifo());
        pagingSim.resetFrames();

        System.out.println("Optimal Page Faults: " + pagingSim.optimal());
        pagingSim.resetFrames();

        System.out.println("LRU Page Faults: " + pagingSim.lru());
        pagingSim.resetFrames();

        System.out.println("LFU Page Faults: " + pagingSim.lfu());
        pagingSim.resetFrames();

        System.out.println("MRU Page Faults: " + pagingSim.mru());
        pagingSim.resetFrames();

        System.out.println("Clock Page Faults: " + pagingSim.clock());
    }

    private void resetFrames() {
        frames.clear();
    }
}
