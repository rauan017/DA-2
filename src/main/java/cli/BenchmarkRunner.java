package cli;

import algorithms.InsertionSort;
import metrics.CsvWriter;
import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        String algo = "InsertionSort";
        int n = 1000;
        String distribution = "random";
        int trials = 3;

        for (String arg : args) {
            if (arg.startsWith("--algo=")) algo = arg.substring(7);
            else if (arg.startsWith("--n=")) n = Integer.parseInt(arg.substring(4));
            else if (arg.startsWith("--distribution=")) distribution = arg.substring(15);
            else if (arg.startsWith("--trials=")) trials = Integer.parseInt(arg.substring(9));
        }

        for (int t = 0; t < trials; t++) {
            int[] data = generateArray(n, distribution);
            PerformanceTracker m;
            switch (algo) {
                case "InsertionSort":
                default:
                    m = InsertionSort.runWithMetrics(data, distribution);
                    break;
            }
            CsvWriter.appendRow("docs/performance.csv", m);
            System.out.printf("%s n=%d trial=%d -> %.3f ms, comps=%d, swaps=%d%n",
                    m.algorithm(), m.n(), t + 1, m.elapsedMs(), m.getComparisons(), m.getSwaps());
        }
    }

    private static int[] generateArray(int n, String distribution) {
        Random rnd = new Random(42 + n);
        int[] a = new int[n];
        switch (distribution) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reverse":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            case "nearly-sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                for (int i = 0; i < n / 10; i++) {
                    int i1 = rnd.nextInt(n);
                    int i2 = rnd.nextInt(n);
                    int tmp = a[i1];
                    a[i1] = a[i2];
                    a[i2] = tmp;
                }
                break;
            case "random":
            default:
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt(1_000_000);
                break;
        }
        return a;
    }
}
