package utilitiesTest;

import org.junit.jupiter.api.Test;
import utilities.FileValidation;
import java.nio.file.attribute.FileTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class FileValidationTest {

    @Test
    void testValidateFileExistsAndMatchesAttributes() throws IOException {
        Path filePath = Files.createTempFile("testFile", ".txt");
        long size = Files.size(filePath);
        FileTime lastModified = Files.getLastModifiedTime(filePath);
        assertTrue(FileValidation.validateFile(filePath.toString(), size, filePath.getParent().toString(), lastModified));
        Files.delete(filePath);
    }

    @Test
    void testValidateFileSizeMismatch() throws IOException {
        Path filePath = Files.createTempFile("testFile", ".txt");
        FileTime lastModified = Files.getLastModifiedTime(filePath);
        assertFalse(FileValidation.validateFile(filePath.toString(), 9999L, filePath.getParent().toString(), lastModified), "Validation should fail due to size mismatch");
        Files.delete(filePath);
    }

    @Test
    void testValidateFileLastModifiedMismatch() throws IOException {
        Path filePath = Files.createTempFile("testFile", ".txt");
        long size = Files.size(filePath);
        FileTime wrongTime = FileTime.fromMillis(System.currentTimeMillis() + 10000);
        assertFalse(FileValidation.validateFile(filePath.toString(), size, filePath.getParent().toString(), wrongTime), "Validation should fail due to last modified mismatch");
        Files.delete(filePath);
    }
}

