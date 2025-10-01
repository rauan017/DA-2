package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    @Test
    void empty() {
        int[] a = {};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "empty", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void single() {
        int[] a = {7};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "single", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{7}, a);
    }

    @Test
    void duplicates() {
        int[] a = {3,1,2,3,1,2};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "duplicates", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{1,1,2,2,3,3}, a);
    }

    @Test
    void sorted() {
        int[] a = {1,2,3,4,5};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "sorted", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{1,2,3,4,5}, a);
    }

    @Test
    void reverse() {
        int[] a = {5,4,3,2,1};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "reverse", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{1,2,3,4,5}, a);
    }

    @Test
    void random() {
        int[] a = {42,7,19,7,13,5,11,100,0,-3};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "random", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{-3,0,5,7,7,11,13,19,42,100}, a);
    }
}
