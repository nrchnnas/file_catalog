// CompareFilePanel.java
//
// The panel that will be displayed after the compare source files button is clicked
// and handles the logic for comparing two source files
//
// Created by Lalida Krairit, 2 November 2024
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CompareFilePanel extends JPanel
{
    private final JButton cancelButton; // button to cancel
    private final JButton compareButton; // button to compare files
    private final JButton fileOneButton; // button for selecting first file to compare
    private final JButton fileTwoButton; // button for selecting second file to compare
    private JButton delFileOneButton; // button to delete first file selection
    private JButton delFileTwoButton; // button to delete second file selection
    public final MyTag fileOneTag; // tag to display first file information
    private final MyTag fileTwoTag; // tag to display second file information

    //Arguments:
    //      -closeListener: listener to actions
    //      -userChooseFile: defines concurrent task
    //      -fileTable: reference to the file table
    public CompareFilePanel(ActionListener closeListener, Runnable userChooseFile, FileTable fileTable)
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        setOpaque(false);

        fileOneTag = new MyTag("First file: ", "", true);
        fileOneTag.setVisible(false);
        fileTwoTag = new MyTag("Second file: ", "", true);
        fileTwoTag.setVisible(false);

        //create selection button
        fileOneButton = createSelButton("Choose First File", userChooseFile, fileTable, this::setFirstTag);
        fileTwoButton = createSelButton("Choose Second File", userChooseFile, fileTable, this::setSecondTag);
        //create delete button, if they press it resets selection
        delFileOneButton = createDelButton(() -> resetSelection(fileOneTag, fileOneButton, delFileOneButton));
        delFileTwoButton = createDelButton(() -> resetSelection(fileTwoTag, fileTwoButton, delFileTwoButton));

        compareButton = new JButton("Compare source files");
        compareButton.addActionListener(e -> openComparisonPopup());
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(closeListener);

        //-----------------------Top Panel--------------------------
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(fileOneButton); topPanel.add(fileOneTag); topPanel.add(delFileOneButton);
        topPanel.add(fileTwoButton); topPanel.add(fileTwoTag); topPanel.add(delFileTwoButton);

        //-----------------------Lower Panel--------------------------
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lowerPanel.add(compareButton);
        lowerPanel.add(cancelButton);

        //-----------------------Main Panel--------------------------
        add(topPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);

        fileTable.getTable().getSelectionModel().addListSelectionListener(e ->
        {
            boolean rowSelected = fileTable.isRowSelected();
            updateButtonState(fileOneButton, rowSelected && !fileOneTag.isVisible());
            updateButtonState(fileTwoButton, rowSelected && fileOneTag.isVisible() && !fileTwoTag.isVisible());
        });
    }

    //Updates button state to be clickable and change text font accordingly
    //Arguments:
    //      -button: button being updated
    //      -enabled: true/false for if it should be clickable
    private void updateButtonState(JButton button, boolean enabled)
    {
        button.setEnabled(enabled);
        button.setForeground(enabled ? Color.BLACK : Color.GRAY);
    }

    //Creates selection button that when user selects table row and then clicks the selection button, it will create a tag
    //Arguments:
    //      -label: label for the button
    //      -userChooseFile: runnable that runs to check whether table has chosen file from table
    //      -fileTable: reference to the file table
    //      -setFileFunction: function that will either set the first file tag or second file tag depending on button created
    // Returns the button to be created
    private JButton createSelButton(String label, Runnable userChooseFile, FileTable fileTable, java.util.function.Consumer<String> setTagFunction)
    {
        JButton button = new JButton(label);
        updateButtonState(button, false);
        button.addActionListener(e -> 
        {
            userChooseFile.run();
            String selectedFileName = fileTable.getSelectedPathName();
            if (selectedFileName != null)
            {
                setTagFunction.accept(selectedFileName);
            }
        });
        return button;
    }

    //Creates a delete button
    //Arguments:
    //      -resetAction: runnable that controls when user clicks delete button
    //Returns delete button to be created
    private JButton createDelButton(Runnable resetAction)
    {
        JButton button = new JButton(new ImageIcon("FileCatalogApplication/src/images/Delete.png"));
        button.setVisible(false);
        button.addActionListener(e -> resetAction.run());
        return button;
    }

    //Sets first tag and makes second button uneditable at first
    //Arguments:
    //      -fileName: the file name to be set to tag
    public void setFirstTag(String fileName)
    {
        setSelection(fileOneTag, fileOneButton, delFileOneButton, fileName);
        updateButtonState(fileTwoButton, false);
    }

    //Sets second tag
    //Arguments:
    //      -fileName: the file name to be set to tag
    public void setSecondTag(String fileName)
    {
        setSelection(fileTwoTag, fileTwoButton, delFileTwoButton, fileName);
    }

    //Removes selection button and sets the tag info when user has selected a file
    //Arguments:
    //      -tag: tag to be set and shown
    //      -selectButton: button to be hidden
    //      -deleteButton: button to be shown
    //      -fileName: file name to be set in the tag
    private void setSelection(MyTag tag, JButton selectButton, JButton deleteButton, String fileName)
    {
        tag.setName(fileName);
        tag.setVisible(true);
        selectButton.setVisible(false);
        deleteButton.setVisible(true);
    }

    //Brings back the selection button when user has pressed the delete button
    //Arguments:
    //      -tag: the tag to be hidden
    //      -selectButton: select button to be shown
    //      -deleteButton: delete button to be hidden
    private void resetSelection(MyTag tag, JButton selectButton, JButton deleteButton)
    {
        tag.setVisible(false);
        deleteButton.setVisible(false);
        updateButtonState(selectButton, true);
        selectButton.setVisible(true);
    }

    //Opens the comparison display popup
    private void openComparisonPopup()
    {
        //TO DO: change it to get the content instead of name
        new ComparisonDisplay(fileOneTag.getName(), fileTwoTag.getName()).setVisible(true);
    }
}
