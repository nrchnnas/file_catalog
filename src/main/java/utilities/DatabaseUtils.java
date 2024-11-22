package utilities;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtils {
    private static final Logger logger = Logger.getLogger(DatabaseUtils.class.getName());
    private static final String DB_FILE = "data/catalog.db";

    private DatabaseUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the database file exists
            ensureDatabaseExists();

            // Dynamically get the database path relative to the JAR location
            String dbPath = new File(DB_FILE).getAbsolutePath();
            String url = "jdbc:sqlite:" + dbPath;

            logger.info("Connecting to database at: " + dbPath);
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to connect to the database", e);
            throw new SQLException("Database connection failed. Please check the database path.", e);
        }
    }

    /**
     * Ensures that the database file exists. Creates the database file and directories if needed.
     */
    private static void ensureDatabaseExists() {
        File dbFile = new File(DB_FILE);

        try {
            // Create the parent directories if they don't exist
            File parentDir = dbFile.getParentFile();
            if (!parentDir.exists()) {
                if (parentDir.mkdirs()) {
                    logger.info("Created directories for database: " + parentDir.getAbsolutePath());
                } else {
                    logger.warning("Failed to create directories for database: " + parentDir.getAbsolutePath());
                }
            }

            // Create the database file if it doesn't exist
            if (!dbFile.exists()) {
                if (dbFile.createNewFile()) {
                    logger.info("Created new database file: " + dbFile.getAbsolutePath());
                } else {
                    logger.warning("Failed to create database file: " + dbFile.getAbsolutePath());
                }
            } else {
                logger.info("Database file already exists: " + dbFile.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create database file or directories", e);
        }
    }
}

//package utilities;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class DatabaseUtils {
//    private static final Logger logger = Logger.getLogger(DatabaseUtils.class.getName());
//
//    // Retrieve database URL from environment variable, with default fallback to absolute path for testing
//    private static final String URL = System.getenv("DATABASE_URL") != null ?
//            System.getenv("DATABASE_URL") : "jdbc:sqlite:C:/Users/thave/OneDrive/Documents/GitHub/file_catalog/data/catalog.db";
//
//    // Private constructor to prevent instantiation
//    private DatabaseUtils() {
//        throw new UnsupportedOperationException("DatabaseUtils is a utility class and cannot be instantiated.");
//    }
//
//    public static Connection getConnection() throws SQLException {
//        try {
//            return DriverManager.getConnection(URL);
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Failed to connect to the database at URL: " + URL, e);
//            throw new SQLException("Failed to connect to the database at URL: " + URL + ". Please check the environment setup.", e);
//        }
//    }
//}
