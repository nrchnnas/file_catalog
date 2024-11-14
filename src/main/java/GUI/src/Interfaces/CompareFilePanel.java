// CompareFilePanel.java
//
// The panel that will be displayed after the compare source files button is clicked
// and handles the logic for comparing two source files for each options
//
// Created by Lalida Krairit, 2 November 2024
//

package GUI.src.Interfaces;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CompareFilePanel extends JPanel
{
    private final JPanel modeSelectionPanel; // Panel for mode buttons
    private final JPanel fileSelectionPanel; // Panel for file selection buttons
    private final JButton cancelModeButton; // button to cancel mode selection
    private final JButton cancelFilSelButton; // button to cancel file selection
    private final JButton compareButton; // button to compare files
    private final JButton fileOneButton; // button for selecting first file to compare
    private final JButton fileTwoButton; // button for selecting second file to compare
    private JButton delFileOneButton; // button to delete first file selection
    private JButton delFileTwoButton; // button to delete second file selection
    public final MyTag fileOneTag; // tag to display first file information
    private final MyTag fileTwoTag; // tag to display second file information
    private FileTable fileTable; // reference to file table
    private CatalogTable catalogTable; // reference to catalog table
    private final CardLayout cardLayout; // Card layout to switch panels
    private String comparisonMode; // stores the comparison mode ("diskDisk", "catCat", "diskCat")

    //Arguments:
    //      -closeListener: listener to actions
    //      -userChooseFile: defines concurrent task
    //      -fileTable: reference to the file table
    //      -catalogTable: reference to catalog table
    public CompareFilePanel(ActionListener closeListener, Runnable userChooseFile, FileTable fileTable, CatalogTable catalogTable)
    {
        this.fileTable = fileTable;
        this.catalogTable = catalogTable;
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        setOpaque(false);

        //-----------------Mode Selection Panel--------------------
        JButton compareDiskToDiskButton = new JButton("Compare Disk to Disk");
        compareDiskToDiskButton.addActionListener(e -> showFileSelPanel("diskDisk"));

        JButton compareCatToCatButton = new JButton("Compare Catalog to Catalog");
        compareCatToCatButton.addActionListener(e -> showFileSelPanel("catCat"));

        cancelModeButton = new JButton("Cancel");
        cancelModeButton.addActionListener(closeListener);

        modeSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modeSelectionPanel.add(compareDiskToDiskButton);
        modeSelectionPanel.add(compareCatToCatButton);
        modeSelectionPanel.add(cancelModeButton);

        //-----------------File Selection Panel--------------------

        fileOneTag = new MyTag("First file: ", "", true);
        fileOneTag.setVisible(false);
        fileTwoTag = new MyTag("Second file: ", "", true);
        fileTwoTag.setVisible(false);

        //create selection button
        fileOneButton = createSelButton("Choose First File", userChooseFile, this::setFirstTag);
        fileTwoButton = createSelButton("Choose Second File", userChooseFile, this::setSecondTag);
        //create delete button, if they press it resets selection
        delFileOneButton = createDelButton(() -> resetSelection(fileOneTag, fileOneButton, delFileOneButton));
        delFileTwoButton = createDelButton(() -> resetSelection(fileTwoTag, fileTwoButton, delFileTwoButton));

        compareButton = new JButton("Compare source files");
        compareButton.addActionListener(e -> openComparisonPopup());
        compareButton.setEnabled(false);
        cancelFilSelButton = new JButton("Cancel");
        cancelFilSelButton.addActionListener(closeListener);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(fileOneButton); topPanel.add(fileOneTag); topPanel.add(delFileOneButton);
        topPanel.add(fileTwoButton); topPanel.add(fileTwoTag); topPanel.add(delFileTwoButton);

        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lowerPanel.add(compareButton);
        lowerPanel.add(cancelFilSelButton);

        fileSelectionPanel = new JPanel();
        fileSelectionPanel.add(topPanel, BorderLayout.CENTER);
        fileSelectionPanel.add(lowerPanel, BorderLayout.SOUTH);

        //-----------------------Main Panel--------------------------
        add(modeSelectionPanel, "ModeSelection");
        add(fileSelectionPanel, "FileSelection");
        cardLayout.show(this, "ModeSelection");     // Show mode selection panel initially

        fileTable.getTable().getSelectionModel().addListSelectionListener(e -> updateSelButton());
        catalogTable.getTable().getSelectionModel().addListSelectionListener(e -> updateSelButton());
    }

    // Method to show the file selection panel and set the comparison mode
    // Arguments:
    //      -mode: mode user selects
    public void showFileSelPanel(String mode)
    {
        this.comparisonMode = mode;
        cardLayout.show(this, "FileSelection");
        revalidate();
        repaint();
        updateSelButton();
    }

    // Updates button states based on the current comparison mode and selection
    private void updateSelButton() {
        boolean fileTableRowSelected = fileTable != null && fileTable.isRowSelected();
        boolean catalogTableRowSelected = catalogTable != null && catalogTable.isRowSelected();

        if (comparisonMode == null)
        {
            return;
        }

        switch (comparisonMode) {
            case "diskDisk":
                updateButtonState(fileOneButton, fileTableRowSelected && fileOneTag != null && !fileOneTag.isVisible());
                updateButtonState(fileTwoButton, fileTableRowSelected && fileOneTag != null && fileOneTag.isVisible() && !fileTwoTag.isVisible());
                break;

            case "catCat":
                updateButtonState(fileOneButton, catalogTableRowSelected && fileOneTag != null && !fileOneTag.isVisible());
                updateButtonState(fileTwoButton, catalogTableRowSelected && fileOneTag != null && fileOneTag.isVisible() && !fileTwoTag.isVisible());
                break;
        }
        compareButton.setEnabled(fileOneTag != null && fileOneTag.isVisible() && fileTwoTag != null && fileTwoTag.isVisible());
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
    //      -setFileFunction: function that will either set the first file tag or second file tag depending on button created
    // Returns the button to be created
    private JButton createSelButton(String label, Runnable userChooseFile, java.util.function.Consumer<String> setTagFunction)
    {
        JButton button = new JButton(label);
        updateButtonState(button, false);
        button.addActionListener(e ->
        {
            userChooseFile.run();
            String selectedFileName = null;
            if ("diskDisk".equals(comparisonMode) || ("diskCat".equals(comparisonMode) && !fileOneTag.isVisible()))
            {
                selectedFileName = fileTable.getSelectedPathName();
            } else if ("catCat".equals(comparisonMode) || ("diskCat".equals(comparisonMode) && fileOneTag.isVisible()))
            {
                selectedFileName = catalogTable.getSelectedPathName();
            }
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
        JButton button = new JButton(new ImageIcon("src/main/java/assets/Delete.png"));
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
        updateSelButton();
    }

    //Sets second tag
    //Arguments:
    //      -fileName: the file name to be set to tag
    public void setSecondTag(String fileName)
    {
        setSelection(fileTwoTag, fileTwoButton, delFileTwoButton, fileName);
        updateSelButton();
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
        updateSelButton();
    }

    //Opens the comparison display popup
    private void openComparisonPopup()
    {
        //TO DO: change it to get the content instead of name
        new ComparisonDisplay(fileOneTag.getName(), fileTwoTag.getName()).setVisible(true);
    }

    //Gets the comparison mode user selects
    //Returns:
    //      -comparisonMode: comparison mode
    public String getComparisonMode()
    {
        return comparisonMode;
    }
}
