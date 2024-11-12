package utilities;

import java.text.SimpleDateFormat;

public class DirectoryContent {
    private final String name;
    private final boolean isDirectory;
    private final String path;
    private final String extension;
    private final long size;
    private final String lastModified;

    public DirectoryContent(String name, boolean isDirectory, String path, String extension, long size, String lastModified) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.path = path;
        this.extension = extension;
        this.size = size;
        this.lastModified = lastModified;
    }

    // Getters
    public String getName() { return name; }
    public boolean isDirectory() { return isDirectory; }
    public String getPath() { return path; }
    public String getExtension() { return extension; }
    public long getSize() { return size; }
    public String getLastModified() { return lastModified; }

    @Override
    public String toString() {
        return "DirectoryContent{" +
                "name='" + name + '\'' +
                ", isDirectory=" + isDirectory +
                ", path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                ", lastModified='" + lastModified + '\'' +
                '}';
    }
}

