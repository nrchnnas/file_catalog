package utilities;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainUtilities {

    private static final Logger logger = Logger.getLogger(MainUtilities.class.getName());

    // Private constructor to prevent instantiation
    private MainUtilities() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void addFileToCatalog(File file, String annotation, String modificationDate, String fileType) {
        if (file == null || !file.exists()) {
            logger.warning("File does not exist or is null.");
            return;
        }

        // Get file information
        String fileName = file.getName();
        String filePath = file.getAbsolutePath();
        long fileSize = file.length(); // Get the file size in bytes

        // Add file to catalog
        FileCatalog.addFile(fileName, filePath, annotation, modificationDate, fileType, fileSize);
        logger.info("File added to catalog with size: " + fileSize + " bytes");
    }

    public static void displayAllFilesInCatalog() {
        List<FileRecord> files = FileCatalog.getAllFiles();
        if (files.isEmpty()) {
            logger.info("No files found in catalog.");
        } else {
            for (FileRecord file : files) {
                System.out.println("ID: " + file.getId());
                System.out.println("Name: " + file.getFileName());
                System.out.println("Path: " + file.getFilePath());
                System.out.println("Annotation: " + file.getAnnotation());
                System.out.println("Modification Date: " + file.getModificationDate());
                System.out.println("Type: " + file.getFileType());
                System.out.println("Size: " + file.getFileSize() + " bytes"); // Display the file size
                System.out.println("----------------------------------");
            }
        }
    }

    public static void updateFileAnnotation(int fileId, String newAnnotation) {
        FileCatalog.updateAnnotation(fileId, newAnnotation);
        logger.info("Updated annotation for file ID: " + fileId);
    }

    public static void updateFileModificationDate(int fileId, String newDate) {
        FileCatalog.updateModificationDate(fileId, newDate);
        logger.info("Updated modification date for file ID: " + fileId);
    }

    public static void updateFileType(int fileId, String newFileType) {
        FileCatalog.updateFileType(fileId, newFileType);
        logger.info("Updated file type for file ID: " + fileId);
    }

    public static void deleteFileFromCatalog(int fileId) {
        FileCatalog.deleteFile(fileId);
        logger.info("Deleted file with ID: " + fileId + " from catalog.");
    }

    private static void testFileComparison(String filePath1, String filePath2) {
        logger.info("Comparing files line by line:");
        try {
            FileComparison.compareFiles(filePath1, filePath2);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during file comparison: " + e.getMessage());
        }
    }

    private static void testDiskComparison(String filePath1, String filePath2) {
        try {
            boolean metadataMatch = DiskComparison.compareFileMetadata(filePath1, filePath2);
            logger.info(metadataMatch
                    ? "File metadata match for " + filePath1 + " and " + filePath2
                    : "File metadata differ for " + filePath1 + " and " + filePath2);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during disk comparison: " + e.getMessage());
        }
    }

    private static void listDirectoryContents(String directoryPath) {
        List<DirectoryContent> contents = DiskReader.listDirectoryContents(directoryPath);
        logger.info("Contents of directory " + directoryPath + ":");
        contents.forEach(content -> logger.info(content.toString()));
    }

    private static void listSubdirectories(String directoryPath) {
        List<DirectoryContent> subdirectories = DiskReader.listSubdirectories(directoryPath);
        logger.info("Subdirectories in " + directoryPath + ":");
        subdirectories.forEach(subdirectory -> logger.info(subdirectory.toString()));
    }

    private static void getParentDirectory(String directoryPath) {
        DirectoryContent parentDirectory = DiskReader.getParentDirectory(directoryPath);
        if (parentDirectory != null) {
            logger.info("Parent directory: " + parentDirectory.toString());
        } else {
            logger.warning("No parent directory found for: " + directoryPath);
        }
    }

    private static void retrieveFileContent(String filePath) {
        String content = FileContent.retrieveFileContent(filePath);
        if (content != null) {
            logger.info("File content: " + content);
        }
    }

    private static boolean validateFile(String filePath, long expectedSize, String expectedPath, FileTime expectedLastModified) {
        boolean isValid = FileValidation.validateFile(filePath, expectedSize, expectedPath, expectedLastModified);
        return isValid;
//        logger.info(isValid
//                ? "File validation successful for: " + filePath
//                : "File validation failed for: " + filePath);
    }
}

