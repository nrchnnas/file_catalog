// AddToCatalogPanel.java
//
// The panel that will be displayed after the add to catalog button is clicked
// and handles the logic when a file is added to the catalog along with its annotation
//
// Created by Lalida Krairit, 30 October 2024
//

package GUI.src;
import utilities.DirectoryContent;
import utilities.FileCatalog;
import utilities.MainUtilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;

public class AddToCatalogPanel extends JPanel implements ActionListener
{
    JButton cancelButton; //button to cancel
    JButton addButton; //button to add to catalog
    JTextArea addAnnotationField; //text field for annotation
    private static final String PLACEHOLDER_TEXT = "Add an annotation: "; //placeholder text inside the text field
    private String pathName; //pathname of selected file
    private final Runnable refreshCatalog; // Refresh callback
    private MainFrame mainFrame; // reference to mainFrame

    public AddToCatalogPanel(String pathName, ActionListener closeListener, Runnable refreshCatalog, MainFrame mainFrame)
    {
        setLayout(new FlowLayout());
        setOpaque(false);
        this.pathName = pathName;
        this.refreshCatalog = refreshCatalog;
        this.mainFrame = mainFrame;

        addAnnotationField = new JTextArea(PLACEHOLDER_TEXT);
        addAnnotationField.setLineWrap(true);
        addAnnotationField.setWrapStyleWord(true);
        addAnnotationField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        addAnnotationField.setPreferredSize(new Dimension(1200, 150));
        addAnnotationField.setForeground(Color.GRAY);

        addAnnotationField.addFocusListener(new FocusListener()
        {
            @Override
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
        // Adds the selected file to catalog when add button is pressed, along with annotation
        if (e.getSource() == addButton)
        {

            File selectedFile = new File(pathName);
            DirectoryContent selectedRecord = new DirectoryContent(
                    selectedFile.getName(),
                    selectedFile.isDirectory(),
                    selectedFile.getPath(),
                    selectedFile.isDirectory() ? "dir" : getFileExtension(selectedFile),
                    selectedFile.length(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(selectedFile.lastModified())
            );
            //check if it is a directory
            if (!selectedRecord.isDirectory())
            {
                //check if its already in directory (duplicate)
                if (FileCatalog.isFileInCatalog(selectedFile.getPath()))
                {
                    JOptionPane.showMessageDialog(mainFrame, "This file is already in the catalog.");
                    return; // Do not proceed with adding the file
                }

                String annotation = addAnnotationField.getText();
                if (annotation.isEmpty() || annotation.equals(PLACEHOLDER_TEXT))
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
                JOptionPane.showMessageDialog(mainFrame, "Cannot add a directory to the catalog");
            }
        }
    }

    //----------------Private Function---------------
    //Gets file extension/file type
    //Returns null if no dotIndex (it's a directory), otherwise return file ext.
    private String getFileExtension(File file)
    {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    //----------------Public Function---------------
    //Updates file path everytime user clicks on new row
    public void updateFilePath(String newPathName)
    {
        this.pathName = newPathName;
    }
}


