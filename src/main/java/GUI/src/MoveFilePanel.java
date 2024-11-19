// MoveFilePanel.java
//
// The panel that will be displayed after the move source files button is clicked
// and handles the logic for moving a source file to a new directory.
//
// Created by Lalida Krairit, 31 October 2024
//

package GUI.src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class MoveFilePanel extends JPanel
{
    private final JButton cancelButton; // button to cancel
    private final JButton moveButton; // button to move file to new directory
    private final JButton chooseNewDirButton; // button for selecting a new directory
    private JButton deleteButton; // button to delete/reset newDirTag
    private final MyTag currFilePathTag; // tag to display current file path
    private final MyTag newFilePathTag; // tag to display new file path
    private String selectedFilePath; // Selected file's current path
    private String destinationDirPath; // Selected destination directory path
    private MainFrame mainFrame; // Reference to main frame
    private boolean isLocked = false; // Lock to prevent changing current file path once set
    private Runnable clearLowerPanel; //clear lower panel of mainFrame

    //Arguments:
    //      -closeListener: listener to actions
    //      -chooseNewPath: defines concurrent task
    //      -fileTable: reference to the file table
    public MoveFilePanel(ActionListener closeListener, Runnable chooseNewPath, String initialFilePath, FileTable fileTable, MainFrame mainFrame, Runnable clearLowerPanel)
    {
        ImageIcon deleteIcon = ImageLoader.loadImage("/assets/Delete.png");

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        setOpaque(false);

        this.selectedFilePath = initialFilePath;
        this.clearLowerPanel = clearLowerPanel;
        this.mainFrame = mainFrame;
        this.isLocked = true;

        currFilePathTag = new MyTag("Current file path: ", initialFilePath, true);
        newFilePathTag = new MyTag("New file path: ", "", false);
        newFilePathTag.setVisible(false); // initially hidden


        chooseNewDirButton = new JButton("Choose New Directory");
        chooseNewDirButton.setEnabled(false);
        chooseNewDirButton.setForeground(Color.GRAY);
        chooseNewDirButton.addActionListener(e -> chooseNewPath.run()); //run the thread that waits for user to choose directory from file table

        deleteButton = new JButton();
        deleteButton.setIcon(deleteIcon);
        deleteButton.setVisible(false); // initially hidden
        deleteButton.addActionListener(e -> resetSelection()); //run this to reset the button after the tag deletion button is clicked

        moveButton = new JButton("Move File to new directory");
        moveButton.addActionListener(e -> moveFile()); //run this to move file

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(closeListener);

        //-----------------------Top Panel--------------------------
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(currFilePathTag);
        topPanel.add(chooseNewDirButton);
        topPanel.add(newFilePathTag);
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
            if (rowSelected && !isLocked)
            {
                String newSelectionPath = fileTable.getSelectedPathName();
                if (newSelectionPath != null)
                {
                    selectedFilePath = newSelectionPath;
                    currFilePathTag.setName(newSelectionPath);
                }
            }
            chooseNewDirButton.setEnabled(rowSelected);
            chooseNewDirButton.setForeground(rowSelected ? Color.BLACK : Color.GRAY);
        });
    }

    //-------------Private Function-------------

    // Moves file to new location, if there's a duplicate prompt users to overwrite or not
    private void moveFile()
    {
        File sourceFile = new File(selectedFilePath);
        File destinationFile = new File(destinationDirPath, sourceFile.getName());

        if (!sourceFile.exists())
        {
            JOptionPane.showMessageDialog(this, "Selected file does not exist.");
            return;
        }

        if (destinationFile.exists())
        {
            int response = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "A file with the same name already exists in the destination. Do you want to overwrite it?",
                    "File Exists",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (response != JOptionPane.YES_OPTION)
            {
                return;
            }
        }
        if (sourceFile.renameTo(destinationFile))
        {
            clearLowerPanel.run();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Failed to move the file. Please try again.");
        }
    }

    // Resets selection button and hides tag and delete button
    //
    private void resetSelection()
    {
        destinationDirPath = null;
        newFilePathTag.setVisible(false);
        deleteButton.setVisible(false);
        chooseNewDirButton.setVisible(true);
        chooseNewDirButton.setText("Choose New Directory");
        chooseNewDirButton.setEnabled(false);
    }

    //-------------Public Function-------------
    // Show and updates the new name of directory tag, show delete button and hide select button
    // Arguments:
    //      -newDirName: new directory name
    //
    public void setNewDirectory(String newDirName)
    {
        File newDir = new File(newDirName);
        if (newDir.isDirectory())
        {
            this.destinationDirPath = newDirName;
            newFilePathTag.setName(newDirName);
            chooseNewDirButton.setVisible(false);
            newFilePathTag.setVisible(true);
            deleteButton.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(mainFrame, "The selected path is a file. Please choose a valid directory.");
        }
    }
}

