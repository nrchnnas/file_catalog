package utilitiesTest;

import org.junit.jupiter.api.Test;
import utilities.FileRecord;
import static org.junit.jupiter.api.Assertions.*;

class FileRecordTest {

    @Test
    void testFileRecordCreation() {
        FileRecord record = new FileRecord(1, "sample.txt", "path/to/sample.txt", "Sample annotation", "2024-01-01", ".txt");
        assertEquals(1, record.getId());
        assertEquals("sample.txt", record.getFileName());
        assertEquals("path/to/sample.txt", record.getFilePath());
        assertEquals("Sample annotation", record.getAnnotation());
        assertEquals("2024-01-01", record.getModificationDate());
        assertEquals(".txt", record.getFileType());
    }

    @Test
    void testToString() {
        FileRecord record = new FileRecord(1, "sample.txt", "path/to/sample.txt", "Sample annotation", "2024-01-01", ".txt");
        String expected = "FileRecord{id=1, fileName='sample.txt', filePath='path/to/sample.txt', annotation='Sample annotation', modificationDate='2024-01-01', fileType='.txt'}";
        assertEquals(expected, record.toString(), "String representation should match");
    }
}

