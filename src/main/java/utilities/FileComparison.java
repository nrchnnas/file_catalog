package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;
import java.util.logging.Logger;

public class FileComparison {
    private static final Logger logger = Logger.getLogger(FileComparison.class.getName());

    public static void compareFiles(String filePath1, String filePath2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {

            String line1 = null;  // Initialize to null to avoid uninitialized variable warning
            String line2 = null;  // Initialize to null to avoid uninitialized variable warning
            int lineNumber = 1;

            while ((line1 = reader1.readLine()) != null || (line2 = reader2.readLine()) != null) {
                if (!Objects.equals(line1, line2)) {
                    System.out.println("Difference found at line " + lineNumber);
                    System.out.println("File 1: " + line1);
                    System.out.println("File 2: " + line2);
                }
                lineNumber++;
            }
        } catch (Exception e) {
            logger.severe("An error occurred while comparing files: " + e.getMessage());
        }
    }
}


