package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiskReader {
    private static final Logger logger = Logger.getLogger(DiskReader.class.getName());

    // Private constructor to prevent instantiation
    private DiskReader() {}

    /**
     * Lists all contents (files and directories) in the specified directory.
     *
     * @param directoryPath The path of the directory to list.
     * @return A list of DirectoryContent objects representing each file and directory.
     */
    public static List<DirectoryContent> listDirectoryContents(String directoryPath) {
        List<DirectoryContent> contents = new ArrayList<>();
        File directory = new File(directoryPath);

        // Check if directory exists and is a directory
        if (!directory.exists() || !directory.isDirectory()) {
            logger.warning(() -> "Directory does not exist or is not a valid directory: " + directoryPath);
            return contents;
        }

        // Retrieve contents; ensure no NullPointerException if listFiles() returns null
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                contents.add(new DirectoryContent(file.getName(), file.isDirectory(), file.getAbsolutePath()));
            }
        } else {
            logger.warning(() -> "Unable to retrieve contents of directory: " + directoryPath);
        }

        return contents;
    }

    /**
     * Lists only the subdirectories in the specified directory.
     *
     * @param directoryPath The path of the directory to check for subdirectories.
     * @return A list of DirectoryContent objects representing each subdirectory.
     */
    public static List<DirectoryContent> listSubdirectories(String directoryPath) {
        List<DirectoryContent> subdirectories = new ArrayList<>();
        File directory = new File(directoryPath);

        // Check if directory exists and is a directory
        if (!directory.exists() || !directory.isDirectory()) {
            logger.warning(() -> "Directory does not exist or is not a valid directory: " + directoryPath);
            return subdirectories;
        }

        // Retrieve subdirectories; ensure no NullPointerException if listFiles() returns null
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    subdirectories.add(new DirectoryContent(file.getName(), true, file.getAbsolutePath()));
                }
            }
        } else {
            logger.warning(() -> "Unable to retrieve contents of directory: " + directoryPath);
        }

        return subdirectories;
    }

    /**
     * Gets the parent directory of the specified directory.
     *
     * @param directoryPath The path of the directory for which to find the parent.
     * @return A DirectoryContent object representing the parent directory, or null if none is found.
     */
    public static DirectoryContent getParentDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File parent = directory.getParentFile();

        // Check if parent directory exists and is a directory
        if (parent != null && parent.isDirectory()) {
            return new DirectoryContent(parent.getName(), true, parent.getAbsolutePath());
        } else {
            logger.warning(() -> "No parent directory found for: " + directoryPath);
            return null;
        }
    }
}


