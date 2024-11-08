// ComparisonDisplay.java
//
// Popup window to display and compare two source files side-by-side
//
// Created by Lalida Krairit, 2 November 2024
//

package GUI.src.Interfaces;
import javax.swing.*;
import java.awt.*;

public class ComparisonDisplay extends JFrame
{

    //Arguments:
    //      -fileOneContent: contains the content from file one
    //      -fileTwoContent: contains the content from file two
    public ComparisonDisplay(String fileOneContent, String fileTwoContent)
    {
        super("File Comparison");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //center

        JTextArea fileOneDisplay = new JTextArea(10, 20);
        JTextArea fileTwoDisplay = new JTextArea(10, 20);
        fileOneDisplay.setText("Contents of " + fileOneContent);
        fileTwoDisplay.setText("Contents of " + fileTwoContent);
        fileOneDisplay.setEditable(false);
        fileTwoDisplay.setEditable(false);

        JPanel displayPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        displayPanel.add(new JScrollPane(fileOneDisplay));
        displayPanel.add(new JScrollPane(fileTwoDisplay));

        add(displayPanel, BorderLayout.CENTER);
    }
}
