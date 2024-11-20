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

    /**
     * Adds a file to the catalog with full validation.
     *
     * @param file             File object to add.
     * @param annotation       Annotation or description for the file.
     * @param modificationDate Modification date as a string.
     * @param fileType         File type as a string.
     */
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
        logger.info(() -> String.format("File added to catalog: %s (Size: %d bytes)", fileName, fileSize));
    }

    /**
     * Displays all files in the catalog with details.
     */
    public static void displayAllFilesInCatalog() {
        List<FileRecord> files = FileCatalog.getAllFiles();
        if (files.isEmpty()) {
            logger.info("No files found in catalog.");
        } else {
            for (FileRecord file : files) {
                System.out.printf(
                        "ID: %d%nName: %s%nPath: %s%nAnnotation: %s%nModification Date: %s%nType: %s%nSize: %d bytes%n----------------------------------%n",
                        file.getId(),
                        file.getFileName(),
                        file.getFilePath(),
                        file.getAnnotation(),
                        file.getModificationDate(),
                        file.getFileType(),
                        file.getFileSize()
                );
            }
        }
    }

    /**
     * Updates a file's annotation in the catalog.
     *
     * @param fileId        The ID of the file.
     * @param newAnnotation The new annotation for the file.
     */
    public static void updateFileAnnotation(int fileId, String newAnnotation) {
        FileCatalog.updateAnnotation(fileId, newAnnotation);
        logger.info(() -> String.format("Updated annotation for file ID: %d", fileId));
    }

    /**
     * Updates a file's modification date in the catalog.
     *
     * @param fileId  The ID of the file.
     * @param newDate The new modification date.
     */
    public static void updateFileModificationDate(int fileId, String newDate) {
        FileCatalog.updateModificationDate(fileId, newDate);
        logger.info(() -> String.format("Updated modification date for file ID: %d", fileId));
    }

    /**
     * Updates a file's type in the catalog.
     *
     * @param fileId     The ID of the file.
     * @param newFileType The new file type.
     */
    public static void updateFileType(int fileId, String newFileType) {
        FileCatalog.updateFileType(fileId, newFileType);
        logger.info(() -> String.format("Updated file type for file ID: %d", fileId));
    }

    /**
     * Deletes a file from the catalog by its ID.
     *
     * @param fileId The ID of the file.
     */
    public static void deleteFileFromCatalog(int fileId) {
        FileCatalog.deleteFile(fileId);
        logger.info(() -> String.format("Deleted file with ID: %d from catalog.", fileId));
    }

    /**
     * Validates a file against its size and last modified time.
     *
     * @param filePath            The file path to validate.
     * @param expectedSize        The expected size of the file.
     * @param expectedLastModified The expected last modified time.
     */
    private static void validateFile(String filePath, long expectedSize, FileTime expectedLastModified) {
        boolean isValid = FileValidation.validateFile(filePath, expectedSize, expectedLastModified);
        logger.info(() -> String.format(
                "File validation %s for: %s",
                isValid ? "successful" : "failed",
                filePath
        ));
    }

    // Other utility methods like testFileComparison, testDiskComparison, listDirectoryContents, etc.
    // remain unchanged unless specific issues arise.
}
