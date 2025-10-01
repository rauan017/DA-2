package algorithms;

import metrics.PerformanceTracker;

import java.util.Arrays;

public final class InsertionSort {
    private InsertionSort() {}

    public static void sort(int[] a, PerformanceTracker m) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            m.incIterations();

            int key = m.readInt(a, i);
            int j = i - 1;
            while (j >= 0) {
                int aj = m.readInt(a, j);
                if (m.cmpInt(aj, key) > 0) {
                    m.writeInt(a, j + 1, aj);
                    j--;
                } else {
                    break;
                }
            }

            m.writeInt(a, j + 1, key);
        }
    }
    public static PerformanceTracker runWithMetrics(int[] input, String datasetLabel) {
        int[] a = Arrays.copyOf(input, input.length);
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", datasetLabel, a.length);
        sort(a, m);
        m.stop();
        return m;
    }
}
