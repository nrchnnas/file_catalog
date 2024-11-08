package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCatalog {

    private static final Logger logger = Logger.getLogger(FileCatalog.class.getName());
    private static final String NO_FILE_FOUND_MESSAGE = "No file found with ID: %d";

    // Private constructor to prevent instantiation
    private FileCatalog() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Initializes the catalog table if it doesn't exist
    public static void initializeCatalog() {
        String createTable = "CREATE TABLE IF NOT EXISTS file_catalog (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "file_name TEXT NOT NULL, " +
                "file_path TEXT NOT NULL, " +
                "annotation TEXT, " +
                "modification_date TEXT, " +
                "file_type TEXT)";
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
            logger.info("Catalog table initialized or already exists.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to initialize catalog table", e);
        }
    }

    // Adds a new file to the catalog with validation
    public static void addFile(String fileName, String filePath, String annotation, String modificationDate, String fileType) {
        if (fileName == null || filePath == null || fileType == null) {
            logger.warning("Invalid input: fileName, filePath, and fileType cannot be null.");
            return;
        }

        String sql = "INSERT INTO file_catalog(file_name, file_path, annotation, modification_date, file_type) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileName);
            pstmt.setString(2, filePath);
            pstmt.setString(3, annotation);
            pstmt.setString(4, modificationDate);
            pstmt.setString(5, fileType);
            pstmt.executeUpdate();
            logger.info("File added to catalog: " + fileName);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add file to catalog", e);
        }
    }

    // Update file name with validation
    public static void updateFileName(int fileId, String newFileName) {
        if (newFileName == null || newFileName.isEmpty()) {
            logger.warning("Invalid file name input.");
            return;
        }

        String sql = "UPDATE file_catalog SET file_name = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFileName);
            pstmt.setInt(2, fileId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info(String.format("File name updated for file ID: %d", fileId));
            } else {
                logger.warning(String.format(NO_FILE_FOUND_MESSAGE, fileId));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Failed to update file name for file ID: %d", fileId), e);
        }
    }

    // Update file path with validation
    public static void updateFilePath(int fileId, String newFilePath) {
        if (newFilePath == null || newFilePath.isEmpty()) {
            logger.warning("Invalid file path input.");
            return;
        }

        String sql = "UPDATE file_catalog SET file_path = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFilePath);
            pstmt.setInt(2, fileId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info(String.format("File path updated for file ID: %d", fileId));
            } else {
                logger.warning(String.format(NO_FILE_FOUND_MESSAGE, fileId));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Failed to update file path for file ID: %d", fileId), e);
        }
    }

    // Update annotation with validation
    public static void updateAnnotation(int fileId, String newAnnotation) {
        if (newAnnotation == null) {
            logger.warning("Invalid annotation input.");
            return;
        }

        String sql = "UPDATE file_catalog SET annotation = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newAnnotation);
            pstmt.setInt(2, fileId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info(String.format("Annotation updated for file ID: %d", fileId));
            } else {
                logger.warning(String.format(NO_FILE_FOUND_MESSAGE, fileId));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Failed to update annotation for file ID: %d", fileId), e);
        }
    }

    // Update modification date with validation
    public static void updateModificationDate(int fileId, String newDate) {
        if (newDate == null || newDate.isEmpty()) {
            logger.warning("Invalid modification date input.");
            return;
        }

        String sql = "UPDATE file_catalog SET modification_date = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDate);
            pstmt.setInt(2, fileId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info(String.format("Modification date updated for file ID: %d", fileId));
            } else {
                logger.warning(String.format(NO_FILE_FOUND_MESSAGE, fileId));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Failed to update modification date for file ID: %d", fileId), e);
        }
    }

    // Update file type with validation
    public static void updateFileType(int fileId, String newFileType) {
        if (newFileType == null || newFileType.isEmpty()) {
            logger.warning("Invalid file type input.");
            return;
        }

        String sql = "UPDATE file_catalog SET file_type = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFileType);
            pstmt.setInt(2, fileId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info(String.format("File type updated for file ID: %d", fileId));
            } else {
                logger.warning(String.format(NO_FILE_FOUND_MESSAGE, fileId));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Failed to update file type for file ID: %d", fileId), e);
        }
    }

    // Retrieves all files from the catalog
    public static List<FileRecord> getAllFiles() {
        List<FileRecord> files = new ArrayList<>();
        String sql = "SELECT * FROM file_catalog";
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FileRecord file = new FileRecord(
                        rs.getInt("id"),
                        rs.getString("file_name"),
                        rs.getString("file_path"),
                        rs.getString("annotation"),
                        rs.getString("modification_date"),
                        rs.getString("file_type")
                );
                files.add(file);
            }
            logger.info("Retrieved all files from catalog.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve files from catalog", e);
        }
        return files;
    }

    // Deletes a file from the catalog by ID with confirmation logging
    public static void deleteFile(int fileId) {
        String sql = "DELETE FROM file_catalog WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fileId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info(String.format("File with ID %d deleted from catalog.", fileId));
            } else {
                logger.warning(String.format(NO_FILE_FOUND_MESSAGE, fileId));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Failed to delete file with ID: %d", fileId), e);
        }
    }
}

