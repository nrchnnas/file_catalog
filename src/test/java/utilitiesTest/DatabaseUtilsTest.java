package utilitiesTest;

import org.junit.jupiter.api.Test;
import utilities.DatabaseUtils;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilsTest {

    @Test
    void testGetConnection() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            assertNotNull(conn, "Database connection should not be null");
            assertTrue(conn.isValid(2), "Database connection should be valid");
        } catch (SQLException e) {
            fail("Database connection should not throw SQLException: " + e.getMessage());
        }
    }

    @Test
    void testConnectionFailsWithInvalidURL() {
        System.setProperty("DATABASE_URL", "jdbc:invalid:url");
        assertThrows(SQLException.class, DatabaseUtils::getConnection, "Should throw SQLException for invalid URL");
        System.clearProperty("DATABASE_URL");
    }
}


