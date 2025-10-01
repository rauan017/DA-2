package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public final class InsertionSortOptimized {
    private InsertionSortOptimized() {}

    public static void sort(int[] a, PerformanceTracker m) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            m.incIterations();
            int key = m.readInt(a, i);

            if (m.cmpInt(a[i - 1], key) <= 0) {
                continue;
            }

            int pos = binarySearch(a, key, 0, i - 1, m);
            int len = i - pos;
            System.arraycopy(a, pos, a, pos + 1, len);
            m.addReads(len);
            m.addWrites(len);
            m.writeInt(a, pos, key);
        }
    }

    private static int binarySearch(int[] a, int key, int low, int high, PerformanceTracker m) {
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = m.cmpInt(key, a[mid]);
            if (cmp < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static PerformanceTracker runWithMetrics(int[] input, String datasetLabel) {
        int[] a = Arrays.copyOf(input, input.length);
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSortOptimized", datasetLabel, a.length);
        sort(a, m);
        m.stop();
        return m;
    }
}
