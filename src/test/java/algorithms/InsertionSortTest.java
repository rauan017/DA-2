package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    @Test
    void emptyArray() {
        int[] a = {};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "empty", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void singleElement() {
        int[] a = {42};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "single", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void allEqualElements() {
        int[] a = {7,7,7,7,7};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "equal", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{7,7,7,7,7}, a);
    }

    @Test
    void alreadySorted() {
        int[] a = {1,2,3,4,5,6,7,8,9};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "sorted", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9}, a);
    }

    @Test
    void reverseSorted() {
        int[] a = {9,8,7,6,5,4,3,2,1};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "reverse", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9}, a);
    }

    @Test
    void withNegatives() {
        int[] a = {5,-1,3,-7,0};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "negatives", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{-7,-1,0,3,5}, a);
    }

    @Test
    void withDuplicates() {
        int[] a = {4,2,2,8,5,2,4};
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "duplicates", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(new int[]{2,2,2,4,4,5,8}, a);
    }

    @Test
    void largeRandomArray() {
        int n = 200;
        int[] a = new Random(42).ints(n, -1000, 1000).toArray();
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        PerformanceTracker m = PerformanceTracker.startOf("InsertionSort", "random200", a.length);
        InsertionSort.sort(a, m);
        m.stop();
        assertArrayEquals(expected, a);
    }
}

