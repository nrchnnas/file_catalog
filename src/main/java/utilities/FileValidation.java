package utilities;

import java.io.File;

public class FileValidation {

    public static boolean validateFile(String filePath, String expectedModificationDate) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File missing: " + filePath);
            return false;
        }

        String actualModificationDate = String.valueOf(file.lastModified());
        if (!actualModificationDate.equals(expectedModificationDate)) {
            System.out.println("Modification date mismatch for: " + filePath);
            return false;
        }
        return true;
    }
}

