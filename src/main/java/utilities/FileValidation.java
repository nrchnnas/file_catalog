package utilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

public class FileValidation {

    public static boolean validateFile(String filePath, long expectedSize, String expectedPath, FileTime expectedLastModified) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File missing: " + filePath);
            return false;
        }

        try {
            boolean sizeMatches = file.length() == expectedSize;
            boolean pathMatches = file.getAbsolutePath().equals(expectedPath);
            boolean lastModifiedMatches = Files.getLastModifiedTime(file.toPath()).equals(expectedLastModified);

            return sizeMatches && pathMatches && lastModifiedMatches;
        } catch (Exception e) {
            System.err.println("Error during file validation: " + e.getMessage());
            return false;
        }
    }
}

