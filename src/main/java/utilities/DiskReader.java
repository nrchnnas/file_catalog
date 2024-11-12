package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiskReader {
    private static final Logger logger = Logger.getLogger(DiskReader.class.getName());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Private constructor to prevent instantiation
    private DiskReader() {}

    /**
     * Lists all contents (files and directories) in the specified directory, including extension, size, and last modified date.
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
                String extension = getFileExtension(file);
                long size = file.length();
                String lastModified = dateFormat.format(file.lastModified());

                contents.add(new DirectoryContent(
                        file.getName(),
                        file.isDirectory(),
                        file.getAbsolutePath(),
                        extension,
                        size,
                        lastModified
                ));
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
                    subdirectories.add(new DirectoryContent(
                            file.getName(),
                            true,
                            file.getAbsolutePath(),
                            "",  // No extension for directories
                            0,   // Size is not relevant for directories
                            dateFormat.format(file.lastModified())
                    ));
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
            return new DirectoryContent(
                    parent.getName(),
                    true,
                    parent.getAbsolutePath(),
                    "",  // No extension for directories
                    0,   // Size is not relevant for directories
                    dateFormat.format(parent.lastModified())
            );
        } else {
            logger.warning(() -> "No parent directory found for: " + directoryPath);
            return null;
        }
    }

    /**
     * Utility method to get the file extension.
     *
     * @param file The file for which to get the extension.
     * @return The file extension as a string, or an empty string if none exists.
     */
    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex > 0 && lastIndex < name.length() - 1) {
            return name.substring(lastIndex + 1);
        } else {
            return "";  // No extension found
        }
    }
}

