// FileInfo.java
//
// Struct for file information
//
// Created by Lalida Krairit, 3 November 2024
//

//TO DO: might not be necessary, right now just for demonstration purposes
public class FileInfo
{
    private String name;
    private String extension;
    private String size;
    private String lastEditedDate;
    private String location;
    private String annotation;

    public FileInfo(String name, String extension, String size, String lastEditedDate, String location, String annotation)
    {
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.lastEditedDate = lastEditedDate;
        this.location = location;
        this.annotation = annotation;
    }

    // Getters for each field
    public String getName() { return name; }
    public String getExtension() { return extension; }
    public String getSize() { return size; }
    public String getLastEditedDate() { return lastEditedDate; }
    public String getLocation() { return location; }
    public String getAnnotation() { return annotation; }
}