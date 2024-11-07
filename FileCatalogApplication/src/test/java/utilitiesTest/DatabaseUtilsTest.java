package utilitiesTest;
import utilities.DatabaseUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtilsTest {
    public static void main(String[] args) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            if (conn != null) {
                System.out.println("Connection to database established.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

