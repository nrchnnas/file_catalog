package utilities;

public class FileRecord {
    private int id;
    private String fileName;
    private String filePath;
    private String annotation;
    private String modificationDate;
    private String fileType;

    public FileRecord(int id, String fileName, String filePath, String annotation, String modificationDate, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.annotation = annotation;
        this.modificationDate = modificationDate;
        this.fileType = fileType;
    }
    
    // Getters
    public int getId() { return id; }
    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public String getAnnotation() { return annotation; }
    public String getModificationDate() { return modificationDate; }
    public String getFileType() { return fileType; }

    // toString method for easy debugging
    @Override
    public String toString() {
        return "FileRecord{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", annotation='" + annotation + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}

