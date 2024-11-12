import utilities.*;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.attribute.FileTime;

public class MainUtilities {
    private static final Logger logger = Logger.getLogger(MainUtilities.class.getName());

    // Retrieve the default directory path from an environment variable or system property
    private static final String DEFAULT_DIRECTORY_PATH =
            System.getProperty("default.directory.path",
                    System.getenv("DEFAULT_DIRECTORY_PATH") != null ? System.getenv("DEFAULT_DIRECTORY_PATH") : "C:/Users/yourusername/Documents");

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

        // Test file comparison and disk comparison
        testFileComparison(DEFAULT_DIRECTORY_PATH + "/file1.txt", DEFAULT_DIRECTORY_PATH + "/file2.txt");
        testDiskComparison(DEFAULT_DIRECTORY_PATH + "/file1.txt", DEFAULT_DIRECTORY_PATH + "/file2.txt");

        // Disk Reader operations
        listDirectoryContents(DEFAULT_DIRECTORY_PATH);
        listSubdirectories(DEFAULT_DIRECTORY_PATH);
        getParentDirectory(DEFAULT_DIRECTORY_PATH);

        // Retrieve file content and validate file
        retrieveFileContent(DEFAULT_DIRECTORY_PATH + "/SampleFile.txt");
        validateFile("path/to/file", 1024L, "expected/path", FileTime.fromMillis(System.currentTimeMillis()));
    }

    private static void testDatabaseConnection() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            if (conn != null) {
                logger.info("Database connection successful.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, () -> "Failed to connect to database: " + e.getMessage());
        }
    }

    private static void addSampleFileToCatalog() {
        String fileName = "SampleFile.txt";
        String filePath = "path/to/SampleFile.txt";
        String annotation = "This is a sample file";
        String modificationDate = "2024-11-01";
        String fileType = ".txt";

        FileCatalog.addFile(fileName, filePath, annotation, modificationDate, fileType);
        logger.info("Sample file added to catalog.");
    }

    private static void updateFileAnnotation(int fileId, String newAnnotation) {
        FileCatalog.updateAnnotation(fileId, newAnnotation);
        logger.info(() -> "Annotation updated for file ID " + fileId);
    }

    private static void updateFileNameInCatalog(int fileId, String newFileName) {
        FileCatalog.updateFileName(fileId, newFileName);
        logger.info(() -> "File name updated for file ID " + fileId);
    }

    private static void listAllFiles() {
        List<FileRecord> files = FileCatalog.getAllFiles();
        logger.info("Listing all files in catalog:");
        files.forEach(file -> logger.info(file.toString()));
    }

    private static void deleteFileFromCatalog(int fileId) {
        FileCatalog.deleteFile(fileId);
        logger.info(() -> "File with ID " + fileId + " deleted from catalog.");
    }

    private static void testFileComparison(String filePath1, String filePath2) {
        logger.info("Comparing files line by line:");
        try {
            FileComparison.compareFiles(filePath1, filePath2);
        } catch (Exception e) {
            logger.log(Level.SEVERE, () -> "Error during file comparison: " + e.getMessage());
        }
    }

    private static void testDiskComparison(String filePath1, String filePath2) {
        try {
            boolean metadataMatch = DiskComparison.compareFileMetadata(filePath1, filePath2);
            logger.info(() -> metadataMatch
                    ? "File metadata match for " + filePath1 + " and " + filePath2
                    : "File metadata differ for " + filePath1 + " and " + filePath2);
        } catch (Exception e) {
            logger.log(Level.SEVERE, () -> "Error during disk comparison: " + e.getMessage());
        }
    }

    private static void listDirectoryContents(String directoryPath) {
        List<DirectoryContent> contents = DiskReader.listDirectoryContents(directoryPath);
        logger.info(() -> "Contents of directory " + directoryPath + ":");
        contents.forEach(content -> logger.info(content.toString()));
    }

    private static void listSubdirectories(String directoryPath) {
        List<DirectoryContent> subdirectories = DiskReader.listSubdirectories(directoryPath);
        logger.info(() -> "Subdirectories in " + directoryPath + ":");
        subdirectories.forEach(subdirectory -> logger.info(subdirectory.toString()));
    }

    private static void getParentDirectory(String directoryPath) {
        DirectoryContent parentDirectory = DiskReader.getParentDirectory(directoryPath);
        if (parentDirectory != null) {
            logger.info(() -> "Parent directory: " + parentDirectory.toString());
        } else {
            logger.warning(() -> "No parent directory found for: " + directoryPath);
        }
    }

    private static void retrieveFileContent(String filePath) {
        String content = FileContent.retrieveFileContent(filePath);
        if (content != null) {
            logger.info(() -> "File content: " + content);
        }
    }

    private static void validateFile(String filePath, long expectedSize, String expectedPath, FileTime expectedLastModified) {
        boolean isValid = FileValidation.validateFile(filePath, expectedSize, expectedPath, expectedLastModified);
        logger.info(() -> isValid
                ? "File validation successful for: " + filePath
                : "File validation failed for: " + filePath);
    }
}

