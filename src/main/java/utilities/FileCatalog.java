package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCatalog {

    private static final Logger logger = Logger.getLogger(FileCatalog.class.getName());

    // Initializes the catalog table if it doesn't exist
    public static void initializeCatalog() {
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS file_catalog (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "file_name TEXT NOT NULL, " +
                    "file_path TEXT NOT NULL, " +
                    "annotation TEXT, " +
                    "modification_date TEXT, " +
                    "file_type TEXT)";
            stmt.execute(createTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize catalog table", e);
        }
    }

    // Adds a new file to the catalog
    public static void addFile(String fileName, String filePath, String annotation, String modificationDate, String fileType) {
        String sql = "INSERT INTO file_catalog(file_name, fileath, annotation, modification_date, file_type) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileName);
            pstmt.setString(2, filePath);
            pstmt.setString(3, annotation);
            pstmt.setString(4, modificationDate);
            pstmt.setString(5, fileType);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add file to catalog", e);
        }
    }

    public static void updateFileName(int fileId, String newFileName) {
        String sql = "UPDATE file_catalog SET file_name = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFileName);
            pstmt.setInt(2, fileId);
            pstmt.executeUpdate();
            System.out.println("File name updated for file ID: " + fileId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update file name for file ID: " + fileId, e);
        }
    }

    public static void updateFilePath(int fileId, String newFilePath) {
        String sql = "UPDATE file_catalog SET file_path = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFilePath);
            pstmt.setInt(2, fileId);
            pstmt.executeUpdate();
            System.out.println("File path updated for file ID: " + fileId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update file path for file ID: " + fileId, e);
        }
    }

    public static void updateAnnotation(int fileId, String newAnnotation) {
        String sql = "UPDATE file_catalog SET annotation = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newAnnotation);
            pstmt.setInt(2, fileId);
            pstmt.executeUpdate();
            System.out.println("Annotation updated for file ID: " + fileId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update annotation for file ID: " + fileId, e);
        }
    }

    public static void updateModificationDate(int fileId, String newDate) {
        String sql = "UPDATE file_catalog SET modification_date = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDate);
            pstmt.setInt(2, fileId);
            pstmt.executeUpdate();
            System.out.println("Modification date updated for file ID: " + fileId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update modification date for file ID: " + fileId, e);
        }
    }

    public static void updateFileType(int fileId, String newFileType) {
        String sql = "UPDATE file_catalog SET file_type = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFileType);
            pstmt.setInt(2, fileId);
            pstmt.executeUpdate();
            System.out.println("File type updated for file ID: " + fileId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update file type for file ID: " + fileId, e);
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
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to retrieve files from catalog", e);
        }
        return files;
    }

    // Deletes a file from the catalog by ID
    public static void deleteFile(int fileId) {
        String sql = "DELETE FROM file_catalog WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fileId);
            pstmt.executeUpdate();
            System.out.println("File with ID " + fileId + " deleted from catalog.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to delete file with ID: " + fileId, e);
        }
    }
}

