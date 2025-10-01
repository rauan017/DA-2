package metrics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public final class CsvWriter {
    private CsvWriter() {}

    public static void appendRow(String path, PerformanceTracker tracker) {
        Path p = Paths.get(path);
        try {
            if (p.getParent() != null) {
                Files.createDirectories(p.getParent());
            }
            boolean needHeader = !Files.exists(p) || Files.size(p) == 0;
            try (var writer = Files.newBufferedWriter(
                    p,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {
                if (needHeader) {
                    writer.write(PerformanceTracker.csvHeader());
                    writer.newLine();
                }
                writer.write(tracker.toCsvRow());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to append CSV row to " + path, e);
        }
    }
}
