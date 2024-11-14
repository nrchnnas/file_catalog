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
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class AddToCatalogPanel extends JPanel implements ActionListener
{
    private FileTable fileTable = new FileTable(this); //the table containing files in catalog
    JButton cancelButton; //button to cancel
    JButton addButton; //button to add to catalog
    JTextArea addAnnotationField; //text field for annotation
    private static final String PLACEHOLDER_TEXT = "Add an annotation: "; //placeholder text inside the text field
    String annotationInput;
    File selectedFile;

    public AddToCatalogPanel(ActionListener closeListener)
    {
        setLayout(new FlowLayout());
        setOpaque(false);

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
        addButton.addActionListener(closeListener);
        add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(closeListener);
        add(cancelButton);
    }
    public DirectoryContent getDiskSelected(JTable table) {
        int selectedRow = table.getSelectedRow();
        List<DirectoryContent> fileRecords = DiskReader.listDirectoryContents("C:/");
        DirectoryContent selectedFile = fileRecords.get(selectedRow);
        if (selectedRow != -1) {
            return selectedFile;
        } else {
            return null;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == addButton && getDiskSelected(fileTable.getTable()) != null){
            selectedFile = new File(getDiskSelected(fileTable.getTable()).getPath());
            if(!selectedFile.isDirectory()){
                System.out.println("call triggered");
                DirectoryContent selectedRecord = getDiskSelected(fileTable.getTable());
                annotationInput = addAnnotationField.getText();
                MainUtilities.addFileToCatalog( selectedFile,annotationInput,selectedRecord.getLastModified(),selectedRecord.getExtension() );
            }
            else{
                System.out.println("entry failed");
            }
        }
    }

}


