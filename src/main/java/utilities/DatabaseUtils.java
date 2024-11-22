package utilities;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
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
            String dbPath = getJarDirectory() + File.separator + DB_FILE;
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
        try {
            String dbPath = getJarDirectory() + File.separator + DB_FILE;
            File dbFile = new File(dbPath);

            // Create the parent directories if they don't exist
            File parentDir = dbFile.getParentFile();
            if (!parentDir.exists() && parentDir.mkdirs()) {
                logger.info("Created directories for database: " + parentDir.getAbsolutePath());
            }

            // Create the database file if it doesn't exist
            if (!dbFile.exists() && dbFile.createNewFile()) {
                logger.info("Created new database file: " + dbFile.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create database file or directories", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An unexpected error occurred while ensuring the database exists", e);
        }
    }

    /**
     * Gets the directory of the running JAR file.
     *
     * @return The absolute path to the directory containing the JAR file.
     */
    private static String getJarDirectory() {
        try {
            return Paths.get(DatabaseUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getParent()
                    .toString();
        } catch (URISyntaxException e) {
            logger.log(Level.WARNING, "Could not resolve JAR directory, using current working directory as fallback.", e);
            return new File(".").getAbsolutePath();
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
