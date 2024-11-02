// MoveFilePanel.java
//
// The panel that will be displayed after the move source files button is clicked
// and handles the logic for moving a source file to a new directory.
//
// Created by Lalida Krairit, 31 October 2024
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MoveFilePanel extends JPanel
{
    private final JButton cancelButton; // button to cancel
    private final JButton moveButton; // button to move file to new directory
    private final JButton chooseNewDirButton; // button for selecting a new directory
    private JButton deleteButton; // button to delete/reset newDirTag
    private final MyTag fileTag; // tag to display file information
    private final MyTag currentDirTag; // tag to display current directory information
    private final MyTag newDirTag; // tag to display selected new directory

    //Arguments:
    //      -closeListener: listener to actions
    //      -userChooseNewDir: defines concurrent task
    //      -fileTable: reference to the file table
    public MoveFilePanel(ActionListener closeListener, Runnable userChooseNewDir, FileTable fileTable)
    {
        setLayout(new BorderLayout());
        setOpaque(false);

        fileTag = new MyTag("Selected file: ", "Program", true);
        currentDirTag = new MyTag("Current directory: ", "JavaProjects", false);
        newDirTag = new MyTag("New directory: ", "", false);
        newDirTag.setVisible(false); // initially hidden

        chooseNewDirButton = new JButton("Choose New Directory");
        chooseNewDirButton.setEnabled(false);
        chooseNewDirButton.setForeground(Color.GRAY);
        chooseNewDirButton.addActionListener(e -> userChooseNewDir.run()); //run the thread that waits for user to choose directory from file table

        deleteButton = new JButton();
        deleteButton.setIcon(new ImageIcon("FileCatalogApplication/src/images/Delete.png"));
        deleteButton.setVisible(false); // initially hidden
        deleteButton.addActionListener(e -> resetSelection()); //run this to reset the button after the tag deletion button is clicked

        //TO DO: when moveButton is clicked, move source files
        moveButton = new JButton("Move File to new directory");
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(closeListener);

        //-----------------------Top Panel--------------------------
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(fileTag);
        topPanel.add(currentDirTag);
        topPanel.add(chooseNewDirButton);
        topPanel.add(newDirTag);
        topPanel.add(deleteButton);

        //-----------------------Lower Panel--------------------------
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lowerPanel.add(moveButton);
        lowerPanel.add(cancelButton);

        //-----------------------Main Panel--------------------------
        add(topPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);

        fileTable.getTable().getSelectionModel().addListSelectionListener(e ->
        {
            boolean rowSelected = fileTable.isRowSelected();
            chooseNewDirButton.setEnabled(rowSelected);
            chooseNewDirButton.setForeground(rowSelected ? Color.BLACK : Color.GRAY);
        });
    }

    //TO DO: get the name of directory from file table then set the name

    // Show and updates the new name of directory tag, show delete button and hide select button
    // Arguments:
    //      -newDirName: new directory name
    public void setNewDirectory(String newDirName)
    {
        newDirTag.setName(newDirName);
        chooseNewDirButton.setVisible(false);
        newDirTag.setVisible(true);
        deleteButton.setVisible(true);
    }

    // Resets selection button and hides tag and delete button
    private void resetSelection()
    {
        newDirTag.setVisible(false);
        deleteButton.setVisible(false);
        chooseNewDirButton.setVisible(true);
        chooseNewDirButton.setText("Choose New Directory");
        chooseNewDirButton.setEnabled(false);
    }
}

