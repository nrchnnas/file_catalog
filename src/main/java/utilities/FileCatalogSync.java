package utilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCatalogSync {

    private static final Logger logger = Logger.getLogger(FileCatalogSync.class.getName());

    /**
     * Syncs the FileCatalog with the current state of the disk.
     */
    public static void syncCatalogWithDisk() {
        // Retrieve all files from the catalog
        List<FileRecord> catalogFiles = FileCatalog.getAllFiles();

        for (FileRecord record : catalogFiles) {
            String filePath = record.getFilePath();
            File file = new File(filePath);

            // Check if the file exists
            if (!file.exists()) {
                // File is missing, remove it from the catalog
                logger.log(Level.WARNING, "File missing on disk. Removing from catalog: {0}", filePath);
                FileCatalog.deleteFile(record.getId());
                continue;
            }

            try {
                // Validate file size and last modified date
                long actualSize = file.length();
                FileTime actualLastModified = Files.getLastModifiedTime(file.toPath());

                if (actualSize != record.getFileSize() ||
                        !actualLastModified.toString().equals(record.getModificationDate())) {
                    // Update the catalog with new values
                    FileCatalog.updateFilePath(record.getId(), filePath);
                    FileCatalog.updateModificationDate(record.getId(), actualLastModified.toString());
                    logger.log(Level.INFO, "Updated catalog entry for file: {0}", filePath);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error syncing file: " + filePath, e);
            }
        }
    }
}
