package utilitiesTest;

import org.junit.jupiter.api.Test;
import utilities.DiskComparison;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class DiskComparisonTest {

    @Test
    void testCompareFileMetadataSameFiles() throws Exception {
        boolean result = DiskComparison.compareFileMetadata("path/to/file1.txt", "path/to/file1_copy.txt");
        assertTrue(result, "File metadata should match for identical files");
    }

    @Test
    void testCompareFileMetadataDifferentFiles() throws Exception {
        boolean result = DiskComparison.compareFileMetadata("path/to/file1.txt", "path/to/file2.txt");
        assertFalse(result, "File metadata should differ for different files");
    }

    @Test
    void testCompareFileMetadataFileNotFound() {
        assertThrows(IOException.class, () -> DiskComparison.compareFileMetadata("nonexistent/file.txt", "another/nonexistent/file.txt"));
    }
}
