package utilities;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiskReader {

    private static final Logger logger = Logger.getLogger(DiskReader.class.getName());

    // Private constructor to prevent instantiation
    private DiskReader() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Lists all files and directories in the specified directory path
    public static void listDirectoryContents(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            logger.log(Level.WARNING, "Directory does not exist or is not a valid directory: {0}", directoryPath);
            return;
        }

        logger.info("Contents of directory: " + directoryPath);
        for (File file : directory.listFiles()) {
            logger.log(Level.INFO, "{0}{1}", new Object[]{
                    (file.isDirectory() ? "[DIR] " : "[FILE] "), file.getName()});
        }
    }
}

