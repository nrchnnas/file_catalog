import utilities.DatabaseUtils;
import utilities.DiskComparison;
import utilities.FileCatalog;
import utilities.FileComparison;
import utilities.FileRecord;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Test database connection
        testDatabaseConnection();

        // Initialize the catalog and perform various operations
        FileCatalog.initializeCatalog();
        addSampleFileToCatalog();
        updateFileAnnotation(1, "Updated annotation");
        listAllFiles();
        deleteFileFromCatalog(1);

        // Test file comparison
        testFileComparison("C:/Users/yourusername/Documents/file1.txt", "C:/Users/yourusername/Documents/file2.txt");

        // Test disk comparison for metadata
        testDiskComparison("C:/Users/yourusername/Documents/file1.txt", "C:/Users/yourusername/Documents/file2.txt");
    }

    private static void testDatabaseConnection() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            if (conn != null) {
                System.out.println("Database connection successful.");
            }
        } catch (Exception e) {
            logger.severe("Failed to connect to database: " + e.getMessage());
        }
    }

    private static void addSampleFileToCatalog() {
        String fileName = "SampleFile.txt";
        String filePath = "path/to/SampleFile.txt";
        String annotation = "This is a sample file";
        String modificationDate = "2024-11-01";
        String fileType = ".txt";

        FileCatalog.addFile(fileName, filePath, annotation, modificationDate, fileType);
        System.out.println("Sample file added to catalog.");
    }

    private static void updateFileAnnotation(int fileId, String newAnnotation) {
        FileCatalog.updateAnnotation(fileId, newAnnotation);
        System.out.println("Annotation updated for file ID " + fileId);
    }

    private static void listAllFiles() {
        List<FileRecord> files = FileCatalog.getAllFiles();
        System.out.println("Listing all files in catalog:");
        for (FileRecord file : files) {
            System.out.println(file);
        }
    }

    private static void deleteFileFromCatalog(int fileId) {
        FileCatalog.deleteFile(fileId);
        System.out.println("File with ID " + fileId + " deleted from catalog.");
    }

    private static void testFileComparison(String filePath1, String filePath2) {
        System.out.println("Comparing files line by line:");
        try {
            FileComparison.compareFiles(filePath1, filePath2);
        } catch (Exception e) {
            logger.severe("Error during file comparison: " + e.getMessage());
        }
    }

    private static void testDiskComparison(String filePath1, String filePath2) {
        try {
            boolean metadataMatch = DiskComparison.compareFileMetadata(filePath1, filePath2);
            if (metadataMatch) {
                System.out.println("File metadata match for " + filePath1 + " and " + filePath2);
            } else {
                System.out.println("File metadata differ for " + filePath1 + " and " + filePath2);
            }
        } catch (Exception e) {
            logger.severe("Error during disk comparison: " + e.getMessage());
        }
    }
}

