package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;


class InsertionSortOptimizedTest {

    @Test
    void basicCorrectness() {
        int[] a = {5,2,9,1,5,6};
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSortOptimized", "basic", a.length);
        InsertionSortOptimized.sort(a, m);
        m.stop();
        assertArrayEquals(expected, a);
    }

    @Test
    void nearlySorted() {
        int[] a = {1,2,3,4,6,5,7,8,9,10};
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSortOptimized", "nearly", a.length);
        InsertionSortOptimized.sort(a, m);
        m.stop();
        assertArrayEquals(expected, a);
    }

    @Test
    void randomLarge() {
        int[] a = new Random(42).ints(200, -1000, 1000).toArray();
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSortOptimized", "random200", a.length);
        InsertionSortOptimized.sort(a, m);
        m.stop();
        assertArrayEquals(expected, a);
    }
}
