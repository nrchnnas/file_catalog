// ContentDisplay.java
//
// Popup window to display source file content
//
// Created by Lalida Krairit, 3 November 2024
//

package GUI.src.Interfaces;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContentDisplay extends JFrame
{
    // Arguments:
    //      -filePath: the full path of the file to display content
    public ContentDisplay(String filePath)
    {
        super("File Content: " + filePath);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        // Text area to display the file content
        JTextArea fileDisplay = new JTextArea();
        fileDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fileDisplay);
        add(scrollPane, BorderLayout.CENTER);

        // Load the content from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                fileDisplay.append(line + "\n");
            }
        } catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, "Error reading file content.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
