package utilities;

public class FileRecord {
    private final int id;
    private final String fileName;
    private final String filePath;
    private final String annotation;
    private final String modificationDate;
    private final String fileType;
    private final long fileSize;

    public FileRecord(int id, String fileName, String filePath, String annotation, String modificationDate, String fileType, long fileSize) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.annotation = annotation;
        this.modificationDate = modificationDate;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    // Getters
    public int getId() { return id; }
    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public String getAnnotation() { return annotation; }
    public String getModificationDate() { return modificationDate; }
    public String getFileType() { return fileType; }
    public long getFileSize() { return fileSize; }

    @Override
    public String toString() {
        return "FileRecord{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", annotation='" + annotation + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
