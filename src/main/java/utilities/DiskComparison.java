package utilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class DiskComparison {

    public static boolean compareFileMetadata(String filePath1, String filePath2) throws Exception {
        Path path1 = Paths.get(filePath1);
        Path path2 = Paths.get(filePath2);

        long size1 = Files.size(path1);
        long size2 = Files.size(path2);

        FileTime lastModified1 = Files.getLastModifiedTime(path1);
        FileTime lastModified2 = Files.getLastModifiedTime(path2);

        return size1 == size2 && lastModified1.equals(lastModified2);
    }
}
