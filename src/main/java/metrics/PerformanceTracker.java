package metrics;

import java.time.Instant;
import java.util.Objects;

public final class PerformanceTracker {
    private final String algorithm;
    private final String dataset;
    private final int n;
    private long startNs;
    private long endNs;
    private long usedBeforeBytes;
    private long usedAfterBytes;
    private long comparisons;
    private long swaps;
    private long reads;
    private long writes;
    private long recursiveCalls;
    private long iterations;
    private final String startedAtIso;

    private PerformanceTracker(String algorithm, String dataset, int n) {
        this.algorithm = Objects.requireNonNull(algorithm);
        this.dataset = Objects.requireNonNull(dataset);
        this.n = n;
        this.startedAtIso = Instant.now().toString();
    }

    public static PerformanceTracker startOf(String algorithm, String dataset, int n) {
        PerformanceTracker m = new PerformanceTracker(algorithm, dataset, n);
        return m.start();
    }

    public PerformanceTracker start() {
        gcHint();
        this.usedBeforeBytes = usedMemory();
        this.startNs = System.nanoTime();
        return this;
    }

    public PerformanceTracker stop() {
        this.endNs = System.nanoTime();
        gcHint();
        this.usedAfterBytes = usedMemory();
        return this;
    }

    public void incComparisons() { comparisons++; }
    public void addComparisons(long k) { comparisons += k; }
    public void incSwaps() { swaps++; }
    public void addSwaps(long k) { swaps += k; }
    public void incReads() { reads++; }
    public void addReads(long k) { reads += k; }
    public void incWrites() { writes++; }
    public void addWrites(long k) { writes += k; }
    public void incRecursiveCalls() { recursiveCalls++; }
    public void addRecursiveCalls(long k) { recursiveCalls += k; }
    public void incIterations() { iterations++; }
    public void addIterations(long k) { iterations += k; }

    public void swapInt(int[] a, int i, int j) {
        addReads(2);
        int tmp = a[i];
        int vj = a[j];
        addWrites(2);
        a[i] = vj;
        a[j] = tmp;
        incSwaps();
    }

    public int readInt(int[] a, int i) {
        incReads();
        return a[i];
    }

    public void writeInt(int[] a, int i, int value) {
        incWrites();
        a[i] = value;
    }

    public int cmpInt(int a, int b) {
        incComparisons();
        return Integer.compare(a, b);
    }

    public long elapsedNs() { return endNs - startNs; }
    public double elapsedMs() { return elapsedNs() / 1_000_000.0; }
    public long memoryDeltaBytes() { return usedAfterBytes - usedBeforeBytes; }
    public String algorithm() { return algorithm; }
    public String dataset() { return dataset; }
    public int n() { return n; }
    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getReads() { return reads; }
    public long getWrites() { return writes; }
    public long getRecursiveCalls() { return recursiveCalls; }
    public long getIterations() { return iterations; }
    public String getStartedAtIso() { return startedAtIso; }

    public static String csvHeader() {
        return String.join(",",
                "started_at",
                "algorithm",
                "dataset",
                "n",
                "elapsed_ns",
                "elapsed_ms",
                "comparisons",
                "swaps",
                "reads",
                "writes",
                "recursive_calls",
                "iterations",
                "memory_delta_bytes"
        );
    }

    public String toCsvRow() {
        return String.join(",",
                startedAtIso,
                escape(algorithm),
                escape(dataset),
                String.valueOf(n),
                String.valueOf(elapsedNs()),
                String.format(java.util.Locale.ROOT, "%.3f", elapsedMs()),
                String.valueOf(comparisons),
                String.valueOf(swaps),
                String.valueOf(reads),
                String.valueOf(writes),
                String.valueOf(recursiveCalls),
                String.valueOf(iterations),
                String.valueOf(memoryDeltaBytes())
        );
    }

    private static String escape(String s) {
        if (s.contains(",") || s.contains("\"")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static long usedMemory() {
        Runtime r = Runtime.getRuntime();
        return r.totalMemory() - r.freeMemory();
    }

    private static void gcHint() {
        System.gc();
        try { Thread.sleep(5); } catch (InterruptedException ignored) {}
    }
}

