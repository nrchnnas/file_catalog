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
//    // Retrieve database URL from environment variable, with default fallback
//    private static final String URL = System.getenv("DATABASE_URL") != null ?
//            System.getenv("DATABASE_URL") : "jdbc:sqlite:data/catalog.db";
//
//    public static Connection getConnection() throws SQLException {
//        try {
//            return DriverManager.getConnection(URL);
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Failed to connect to the database", e);
//            throw e; // rethrow after logging
//        }
//    }
//}

package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtils {
    private static final Logger logger = Logger.getLogger(DatabaseUtils.class.getName());

    // Retrieve database URL from environment variable, with default fallback to absolute path for testing
    private static final String URL = System.getenv("DATABASE_URL") != null ?
            System.getenv("DATABASE_URL") : "jdbc:sqlite:C:/Users/thave/OneDrive/Documents/GitHub/file_catalog/data/catalog.db";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to connect to the database", e);
            throw e; // rethrow after logging
        }
    }
}
