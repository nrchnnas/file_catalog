import utilities.*;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.attribute.FileTime;

public class MainUtilities {
    private static final Logger logger = Logger.getLogger(MainUtilities.class.getName());

    public static void main(String[] args) {
        // Test database connection
        testDatabaseConnection();

        // Initialize the catalog and perform various operations
        FileCatalog.initializeCatalog();
        addSampleFileToCatalog();
        updateFileAnnotation(1, "Updated annotation");
        updateFileNameInCatalog(1, "NewSampleFile.txt");
        listAllFiles();
        deleteFileFromCatalog(1);
        testFileComparison("C:/Users/yourusername/Documents/file1.txt", "C:/Users/yourusername/Documents/file2.txt");
        testDiskComparison("C:/Users/yourusername/Documents/file1.txt", "C:/Users/yourusername/Documents/file2.txt");
        listDirectoryContents("C:/Users/yourusername/Documents");
        retrieveFileContent("C:/Users/yourusername/Documents/SampleFile.txt");
        validateFile("C:/Users/yourusername/Documents/SampleFile.txt", 1024, "C:/Users/yourusername/Documents", FileTime.fromMillis(System.currentTimeMillis()));
    }

    private static void testDatabaseConnection() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            if (conn != null) {
                logger.info("Database connection successful.");
            }
        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.severe("Failed to connect to database: " + e.getMessage());
            }
        }
    }

    private static void addSampleFileToCatalog() {
        String fileName = "SampleFile.txt";
        String filePath = "path/to/SampleFile.txt";
        String annotation = "This is a sample file";
        String modificationDate = "2024-11-01";
        String fileType = ".txt";

        FileCatalog.addFile(fileName, filePath, annotation, modificationDate, fileType);
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Sample file added to catalog.");
        }
    }

    private static void updateFileAnnotation(int fileId, String newAnnotation) {
        FileCatalog.updateAnnotation(fileId, newAnnotation);
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Annotation updated for file ID " + fileId);
        }
    }

    private static void updateFileNameInCatalog(int fileId, String newFileName) {
        FileCatalog.updateFileName(fileId, newFileName);
        if (logger.isLoggable(Level.INFO)) {
            logger.info("File name updated for file ID " + fileId);
        }
    }

    private static void listAllFiles() {
        List<FileRecord> files = FileCatalog.getAllFiles();
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Listing all files in catalog:");
            files.forEach(file -> logger.info(file.toString())); // 
        }
    }

    private static void deleteFileFromCatalog(int fileId) {
        FileCatalog.deleteFile(fileId);
        if (logger.isLoggable(Level.INFO)) {
            logger.info("File with ID " + fileId + " deleted from catalog.");
        }
    }

    private static void testFileComparison(String filePath1, String filePath2) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Comparing files line by line:");
        }
        try {
            FileComparison.compareFiles(filePath1, filePath2);
        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.severe("Error during file comparison: " + e.getMessage());
            }
        }
    }

    private static void testDiskComparison(String filePath1, String filePath2) {
        try {
            boolean metadataMatch = DiskComparison.compareFileMetadata(filePath1, filePath2);
            if (logger.isLoggable(Level.INFO)) {
                logger.info(metadataMatch
                        ? "File metadata match for " + filePath1 + " and " + filePath2
                        : "File metadata differ for " + filePath1 + " and " + filePath2);
            }
        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.severe("Error during disk comparison: " + e.getMessage()); 
            }
        }
    }

    private static void listDirectoryContents(String directoryPath) {
        DiskReader.listDirectoryContents(directoryPath);
    }

    private static void retrieveFileContent(String filePath) {
        String content = FileContent.retrieveFileContent(filePath);
        if (content != null && logger.isLoggable(Level.INFO)) {
            logger.info("File content: " + content);
        }
    }

    private static void validateFile(String filePath, long expectedSize, String expectedPath, FileTime expectedLastModified) {
        boolean isValid = FileValidation.validateFile(filePath, expectedSize, expectedPath, expectedLastModified);
        if (logger.isLoggable(Level.INFO)) {
            logger.info(isValid
                    ? "File validation successful for: " + filePath
                    : "File validation failed for: " + filePath);
        }
    }
}





