package utilitiesTest;

import org.junit.jupiter.api.Test;
import utilities.FileComparison;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class FileComparisonTest {

    @Test
    void testCompareIdenticalFiles() throws IOException {
        File file1 = new File("test1.txt");
        File file2 = new File("test2.txt");
        writeToFile(file1, "This is a test.");
        writeToFile(file2, "This is a test.");
        assertDoesNotThrow(() -> FileComparison.compareFiles(file1.getPath(), file2.getPath()));
        file1.delete();
        file2.delete();
    }

    @Test
    void testCompareDifferentFiles() throws IOException {
        File file1 = new File("test1.txt");
        File file2 = new File("test2.txt");
        writeToFile(file1, "This is a test.");
        writeToFile(file2, "This is another test.");
        assertThrows(Exception.class, () -> FileComparison.compareFiles(file1.getPath(), file2.getPath()), "Different files should throw exception");
        file1.delete();
        file2.delete();
    }

    private void writeToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
}

