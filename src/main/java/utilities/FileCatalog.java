package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FileCatalog {

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
            e.printStackTrace();
        }
    }

    // Adds a new file to the catalog
    public static void addFile(String fileName, String filePath, String annotation, String modificationDate, String fileType) {
        String sql = "INSERT INTO file_catalog(file_name, file_path, annotation, modification_date, file_type) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileName);
            pstmt.setString(2, filePath);
            pstmt.setString(3, annotation);
            pstmt.setString(4, modificationDate);
            pstmt.setString(5, fileType);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Updates the annotation of a file in the catalog
    public static void updateAnnotation(int fileId, String newAnnotation) {
        String sql = "UPDATE file_catalog SET annotation = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newAnnotation);
            pstmt.setInt(2, fileId);
            pstmt.executeUpdate();
            System.out.println("Annotation updated for file ID: " + fileId);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
