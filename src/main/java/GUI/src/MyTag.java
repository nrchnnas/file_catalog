// MyTag.java
//
// This class creates a custom tag for displaying the file/directory name
//
// Created by Lalida Krairit, 31 October 2024
//

package GUI.src;
import javax.swing.*;
import java.awt.*;

public class MyTag extends JPanel
{
    private JLabel iconLabel;    //Label to display the icon
    private JLabel nameLabel;    //Label to display the name of the directory or file
    private JLabel titleLabel;   //Label to display the alt text of the tag
    private ImageIcon documentIcon, folderIcon; //image icons

    //Arguments:
    //      -title: alt text of tag
    //      -name: name of directory or file
    //      -isFile: boolean variable if the tag is file or directory
    public MyTag(String title, String name, boolean isFile)
    {
        documentIcon = ImageLoader.loadImage("/assets/Document.png");
        folderIcon = ImageLoader.loadImage("/assets/Folder.png");

        setLayout(new BorderLayout(0, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        titleLabel = new JLabel(title);
        iconLabel = new JLabel();
        setIcon(isFile);
        nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.BLACK);

        JPanel tagContentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tagContentPanel.setBackground(Color.WHITE);
        tagContentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        tagContentPanel.add(iconLabel);
        tagContentPanel.add(nameLabel);

        add(titleLabel, BorderLayout.NORTH);
        add(tagContentPanel, BorderLayout.CENTER);
    }

    //----------------Private Function---------------

    // Update the icon based on file or directory
    // Arguments:
    //      -isFile: boolean variable if it is a file or directory
    private void setIcon(boolean isFile)
    {
        if (isFile)
        {
            iconLabel.setIcon(documentIcon);
        } else
        {
            iconLabel.setIcon(folderIcon);
        }
    }

    //----------------Public Function---------------

    // Updates the nameLabel
    // Arguments:
    //      -text: text to be updated
    public void setName(String text)
    {
        nameLabel.setText(text);
    }
}

