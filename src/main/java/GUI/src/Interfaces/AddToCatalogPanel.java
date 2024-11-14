// AddToCatalogPanel.java
//
// The panel that will be displayed after the add to catalog button is clicked
// and handles the logic when a file is added to the catalog along with its annotation
//
// Created by Lalida Krairit, 30 October 2024
//

package GUI.src.Interfaces;
import utilities.DirectoryContent;
import utilities.DiskReader;
import utilities.FileCatalog;
import utilities.MainUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class AddToCatalogPanel extends JPanel implements ActionListener
{
    JButton cancelButton; //button to cancel
    JButton addButton; //button to add to catalog
    JTextArea addAnnotationField; //text field for annotation
    private static final String PLACEHOLDER_TEXT = "Add an annotation: "; //placeholder text inside the text field
    private String pathName; //pathname of selected file
    private final Runnable refreshCatalog; // Refresh callback

    public AddToCatalogPanel(String pathName, ActionListener closeListener, Runnable refreshCatalog)
    {
        setLayout(new FlowLayout());
        setOpaque(false);
        this.pathName = pathName;
        this.refreshCatalog = refreshCatalog;

        addAnnotationField = new JTextArea(PLACEHOLDER_TEXT);
        addAnnotationField.setLineWrap(true);
        addAnnotationField.setWrapStyleWord(true);
        addAnnotationField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        addAnnotationField.setPreferredSize(new Dimension(1200, 150));
        addAnnotationField.setForeground(Color.GRAY);

        addAnnotationField.addFocusListener(new FocusListener()
        {
            @Override
            //
            // Removes placeholder text when the user clicks on the text field
            // and replaces with new text and change color
            // Arguments:
            //      e: keyboard event
            //
            public void focusGained(FocusEvent e)
            {
                if (addAnnotationField.getText().equals(PLACEHOLDER_TEXT))
                {
                    addAnnotationField.setText("");
                    addAnnotationField.setForeground(Color.BLACK);
                }
            }

            //
            // Restores placeholder text when the user clicks off the text field
            // and change color back to original
            // Arguments:
            //      e: keyboard event
            //
            @Override
            public void focusLost(FocusEvent e)
            {
                if (addAnnotationField.getText().isEmpty())
                {
                    addAnnotationField.setText(PLACEHOLDER_TEXT);
                    addAnnotationField.setForeground(Color.GRAY);
                }
            }
        });

        add(addAnnotationField);

        //TO DO: when add button clicked, add to catalog + add annotation
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(closeListener);
        add(cancelButton);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == addButton)
        {
            System.out.println("Selected file path: " + pathName);

            File selectedFile = new File(pathName);
            DirectoryContent selectedRecord = new DirectoryContent(
                    selectedFile.getName(),
                    selectedFile.isDirectory(),
                    selectedFile.getPath(),
                    selectedFile.isDirectory() ? "dir" : getFileExtension(selectedFile),
                    selectedFile.length(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(selectedFile.lastModified())
            );
            if (!selectedRecord.isDirectory())
            {
                String annotation = addAnnotationField.getText();
                if (annotation.isEmpty() || annotation.equals("Add an annotation: "))
                {
                   annotation = null;
                }
                MainUtilities.addFileToCatalog(
                        selectedFile,
                        annotation,
                        selectedRecord.getLastModified(),
                        selectedRecord.getExtension()
                );
                refreshCatalog.run();
            } else
            {
                System.out.println("Cannot add a directory to the catalog.");
            }
        }
    }

    private String getFileExtension(File file)
    {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

}


