
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DatabaseUtils {
    private static final Logger logger = Logger.getLogger(DatabaseUtils.class.getName());

    // Retrieve database URL from environment variable, with default fallback
    public static final String URL = "./data/catalog.db";

    // Private constructor to prevent instantiation
    private DatabaseUtils() {
        throw new UnsupportedOperationException("DatabaseUtils is a utility class and cannot be instantiated.");
    }

    public static Connection getConnection() throws SQLException {
        try {
//            return DriverManager.getConnection(DatabaseExtractor.connectToDatabase("/data/catalog.db"));
//            System.out.println("URL:" + URL);
            return DriverManager.getConnection("jdbc:sqlite:" + URL);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to connect to the database", e);
            throw new SQLException("Database connection failed. Please check the URL and your environment setup.", e); // add context to exception
        } catch (Exception e) {
            throw new RuntimeException(e);
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
