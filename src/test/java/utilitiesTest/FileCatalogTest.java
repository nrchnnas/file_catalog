package utilitiesTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.DatabaseUtils;
import utilities.FileCatalog;
import utilities.FileRecord;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileCatalogTest {

    @BeforeAll
    static void setUp() {
        FileCatalog.initializeCatalog();
    }

    @Test
    void testAddFile() {
        FileCatalog.addFile("testFile.txt", "path/to/testFile.txt", "Test annotation", "2024-01-01", ".txt");
        List<FileRecord> files = FileCatalog.getAllFiles();
        assertTrue(files.stream().anyMatch(file -> file.getFileName().equals("testFile.txt")), "File should be added to the catalog");
    }

    @Test
    void testUpdateFileName() {
        FileCatalog.addFile("updateTest.txt", "path/to/updateTest.txt", "Update annotation", "2024-01-01", ".txt");
        List<FileRecord> files = FileCatalog.getAllFiles();
        int fileId = files.get(0).getId();
        FileCatalog.updateFileName(fileId, "updatedFileName.txt");
        files = FileCatalog.getAllFiles();
        assertTrue(files.stream().anyMatch(file -> file.getFileName().equals("updatedFileName.txt")), "File name should be updated in the catalog");
    }

    @Test
    void testDeleteFile() {
        FileCatalog.addFile("deleteTest.txt", "path/to/deleteTest.txt", "Delete annotation", "2024-01-01", ".txt");
        List<FileRecord> files = FileCatalog.getAllFiles();
        int fileId = files.get(0).getId();
        FileCatalog.deleteFile(fileId);
        files = FileCatalog.getAllFiles();
        assertTrue(files.stream().noneMatch(file -> file.getId() == fileId), "File should be deleted from the catalog");
    }
}
