package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileComparison {
    private static final Logger logger = Logger.getLogger(FileComparison.class.getName());

    // Private constructor to prevent instantiation
    private FileComparison() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Compares two files line by line and logs any differences found
    public static void compareFiles(String filePath1, String filePath2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {

            String line1 = null;  // Initialize line variables to null
            String line2 = null;
            int lineNumber = 1;

            while ((line1 = reader1.readLine()) != null || (line2 = reader2.readLine()) != null) {
                if (!Objects.equals(line1, line2)) {
                    logger.info(String.format("Difference found at line %d%nFile 1: %s%nFile 2: %s", lineNumber, line1, line2));
                }
                lineNumber++;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while comparing files", e);
        }
    }
}



