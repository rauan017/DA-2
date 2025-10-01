package metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PerformanceTrackerTest {

    @Test
    void countersAndCsv() {
        PerformanceTracker m = PerformanceTracker.startOf("TestAlgo", "unit", 5);
        m.incComparisons();
        m.addComparisons(2);
        m.incReads();
        m.addReads(3);
        m.incWrites();
        m.incSwaps();
        m.incRecursiveCalls();
        m.incIterations();
        m.stop();

        assertEquals(3, m.getComparisons());
        assertEquals(4, m.getReads());
        assertEquals(1, m.getWrites());
        assertEquals(1, m.getSwaps());
        assertEquals(1, m.getRecursiveCalls());
        assertEquals(1, m.getIterations());
        assertTrue(m.elapsedNs() > 0);

        String header = PerformanceTracker.csvHeader();
        String row = m.toCsvRow();
        assertTrue(header.split(",").length == row.split(",").length);
    }
}
