package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileContent {
    private static final Logger logger = Logger.getLogger(FileContent.class.getName());

    // Private constructor to prevent instantiation
    private FileContent() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Retrieves the content of the specified file as a String
    public static String retrieveFileContent(String filePath) {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            logger.warning("File does not exist: " + filePath);
            return null;
        }

        try {
            return Files.readString(path);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file content for file: " + filePath, e);
            return null;
        }
    }
}
