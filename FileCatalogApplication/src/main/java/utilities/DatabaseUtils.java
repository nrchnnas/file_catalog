package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
     private static final String URL = "jdbc:sqlite:data/catalog.db";

public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL);
}
}