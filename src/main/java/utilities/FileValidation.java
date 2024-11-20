package utilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileValidation {

    private static final Logger logger = Logger.getLogger(FileValidation.class.getName());

    // Private constructor to hide the implicit public one
    private FileValidation() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Validates a file based on its size and last modified time.
     *
     * @param filePath            The path to the file being validated.
     * @param expectedSize        The expected size of the file in bytes.
     * @param expectedLastModified The expected last modified time of the file.
     * @return true if the file exists and matches the expected attributes, false otherwise.
     */
    public static boolean validateFile(String filePath, long expectedSize, FileTime expectedLastModified) {
        File file = new File(filePath);

        if (!file.exists()) {
            logger.log(Level.WARNING, "File missing: {0}", filePath);
            return false;
        }

        try {
            boolean sizeMatches = file.length() == expectedSize;
            boolean lastModifiedMatches = Files.getLastModifiedTime(file.toPath()).equals(expectedLastModified);

            if (!sizeMatches) {
                logger.log(Level.WARNING, "File size mismatch: Expected {0} bytes but found {1} bytes for {2}",
                        new Object[]{expectedSize, file.length(), filePath});
            }

            if (!lastModifiedMatches) {
                logger.log(Level.WARNING, "File last modified time mismatch for: {0}", filePath);
            }

            return sizeMatches && lastModifiedMatches;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during file validation for {0}: {1}", new Object[]{filePath, e.getMessage()});
            return false;
        }
    }
}

