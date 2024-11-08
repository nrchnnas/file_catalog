// ContentDisplay.java
//
// Popup window to display source file content
//
// Created by Lalida Krairit, 3 November 2024
//

package GUI.src.Interfaces;
import javax.swing.*;
import java.awt.*;

public class ContentDisplay extends JFrame
{

    //Arguments:
    //      -fileContent: contains the content from file
    public ContentDisplay(String fileContent)
    {
        super("File Content");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //center

        JTextArea fileDisplay = new JTextArea(10, 20);
        fileDisplay.setText("Contents of " + fileContent);
        fileDisplay.setEditable(false);
        add(fileDisplay, BorderLayout.CENTER);
    }
}
