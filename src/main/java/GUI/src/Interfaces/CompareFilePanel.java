// CompareFilePanel.java
//
// Provides a GUI panel for selecting and comparing two files, with options for file deletion and comparison mode selection.
//
// Created by Lalida Krairit, 2 November 2024
//

package GUI.src.Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.FileComparison;
import utilities.DiskComparison;

public class CompareFilePanel extends JPanel {
    private static final Logger logger = Logger.getLogger(CompareFilePanel.class.getName());

    // GUI components for mode and file selection
    private final JPanel modeSelectionPanel; // Panel for mode buttons
    private final JPanel fileSelectionPanel; // Panel for file selection buttons
    private final JButton cancelModeButton; // Button to cancel mode selection
    private final JButton cancelFileSelButton; // Button to cancel file selection
    private final JButton compareButton; // Button to compare files
    private final JButton fileOneButton; // Button to select first file
    private final JButton fileTwoButton; // Button to select second file
    private JButton delFileOneButton; // Button to delete first file selection
    private JButton delFileTwoButton; // Button to delete second file selection
    public final MyTag fileOneTag; // Tag for first file information
    private final MyTag fileTwoTag; // Tag for second file information
    private FileTable fileTable; // Reference to file table
    private CatalogTable catalogTable; // Reference to catalog table
    private final CardLayout cardLayout; // Card layout to switch between panels
    private String comparisonMode; // Stores comparison mode ("diskDisk" or "catCat")

    /**
     * Constructs the CompareFilePanel with mode selection and file selection options.
     *
     * Arguments:
     * - closeListener: ActionListener for closing the panel.
     * - userChooseFile: Defines the concurrent task for file selection.
     * - fileTable: Reference to the file table.
     * - catalogTable: Reference to the catalog table.
     */
    public CompareFilePanel(ActionListener closeListener, Runnable userChooseFile, FileTable fileTable, CatalogTable catalogTable) {
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

        fileOneButton = createSelButton("Choose First File", userChooseFile, this::setFirstTag);
        fileTwoButton = createSelButton("Choose Second File", userChooseFile, this::setSecondTag);
        delFileOneButton = createDelButton(() -> resetSelection(fileOneTag, fileOneButton, delFileOneButton));
        delFileTwoButton = createDelButton(() -> resetSelection(fileTwoTag, fileTwoButton, delFileTwoButton));

        compareButton = new JButton("Compare source files");
        compareButton.addActionListener(e -> openComparisonPopup());
        compareButton.setEnabled(false);
        cancelFileSelButton = new JButton("Cancel");
        cancelFileSelButton.addActionListener(closeListener);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(fileOneButton); topPanel.add(fileOneTag); topPanel.add(delFileOneButton);
        topPanel.add(fileTwoButton); topPanel.add(fileTwoTag); topPanel.add(delFileTwoButton);

        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lowerPanel.add(compareButton);
        lowerPanel.add(cancelFileSelButton);

        fileSelectionPanel = new JPanel(new BorderLayout());
        fileSelectionPanel.add(topPanel, BorderLayout.CENTER);
        fileSelectionPanel.add(lowerPanel, BorderLayout.SOUTH);

        //-----------------------Main Panel--------------------------
        add(modeSelectionPanel, "ModeSelection");
        add(fileSelectionPanel, "FileSelection");
        cardLayout.show(this, "ModeSelection");

        fileTable.getTable().getSelectionModel().addListSelectionListener(e -> updateSelButton());
        catalogTable.getTable().getSelectionModel().addListSelectionListener(e -> updateSelButton());
    }

    /**
     * Shows the file selection panel based on the chosen mode.
     *
     * Arguments:
     * - mode: Selected comparison mode ("diskDisk" or "catCat").
     */
    public void showFileSelPanel(String mode) {
        this.comparisonMode = mode;
        cardLayout.show(this, "FileSelection");
        revalidate();
        repaint();
        updateSelButton();
    }

    /**
     * Updates button states for file selection based on current comparison mode.
     */
    private void updateSelButton() {
        boolean fileTableRowSelected = fileTable != null && fileTable.isRowSelected();
        boolean catalogTableRowSelected = catalogTable != null && catalogTable.isRowSelected();

        if (comparisonMode == null) return;

        switch (comparisonMode) {
            case "diskDisk":
                updateButtonState(fileOneButton, fileTableRowSelected && !fileOneTag.isVisible());
                updateButtonState(fileTwoButton, fileTableRowSelected && fileOneTag.isVisible() && !fileTwoTag.isVisible());
                break;
            case "catCat":
                updateButtonState(fileOneButton, catalogTableRowSelected && !fileOneTag.isVisible());
                updateButtonState(fileTwoButton, catalogTableRowSelected && fileOneTag.isVisible() && !fileTwoTag.isVisible());
                break;
        }
        compareButton.setEnabled(fileOneTag.isVisible() && fileTwoTag.isVisible());
    }

    /**
     * Updates button state for interactivity and color.
     *
     * Arguments:
     * - button: Button to update.
     * - enabled: Whether the button should be enabled.
     */
    private void updateButtonState(JButton button, boolean enabled) {
        button.setEnabled(enabled);
        button.setForeground(enabled ? Color.BLACK : Color.GRAY);
    }

    /**
     * Creates a selection button with the given label.
     *
     * Arguments:
     * - label: Text on the button.
     * - userChooseFile: Action to perform when file is chosen.
     * - setTagFunction: Function to set the selected file tag.
     */
    private JButton createSelButton(String label, Runnable userChooseFile, java.util.function.Consumer<String> setTagFunction) {
        JButton button = new JButton(label);
        updateButtonState(button, false);
        button.addActionListener(e -> {
            userChooseFile.run();
            String selectedFileName = comparisonMode.equals("diskDisk") ? fileTable.getSelectedPathName() : catalogTable.getSelectedPathName();
            if (selectedFileName != null) setTagFunction.accept(selectedFileName);
        });
        return button;
    }

    /**
     * Creates a delete button to reset file selection.
     *
     * Arguments:
     * - resetAction: Runnable action to reset the selection.
     */
    private JButton createDelButton(Runnable resetAction) {
        JButton button = new JButton(new ImageIcon("src/main/java/assets/Delete.png"));
        button.setVisible(false);
        button.addActionListener(e -> resetAction.run());
        return button;
    }

    /**
     * Sets the first file tag and hides the button after selection.
     *
     * Arguments:
     * - fileName: Name of the selected file.
     */
    public void setFirstTag(String fileName) {
        setSelection(fileOneTag, fileOneButton, delFileOneButton, fileName);
        updateButtonState(fileTwoButton, false);
        updateSelButton();
    }

    /**
     * Sets the second file tag after selection.
     *
     * Arguments:
     * - fileName: Name of the selected file.
     */
    public void setSecondTag(String fileName) {
        setSelection(fileTwoTag, fileTwoButton, delFileTwoButton, fileName);
        updateSelButton();
    }

    /**
     * Sets selection and visibility for file tags.
     *
     * Arguments:
     * - tag: Tag to be set and shown.
     * - selectButton: Button to hide.
     * - deleteButton: Delete button to show.
     * - fileName: Name of the file to be set in the tag.
     */
    private void setSelection(MyTag tag, JButton selectButton, JButton deleteButton, String fileName) {
        tag.setName(fileName);
        tag.setVisible(true);
        selectButton.setVisible(false);
        deleteButton.setVisible(true);
    }

    /**
     * Brings back the selection button and hides the delete button when user deletes a selected file.
     *
     * Arguments:
     * - tag: The tag to be hidden.
     * - selectButton: The selection button to show.
     * - deleteButton: The delete button to hide.
     */
    private void resetSelection(MyTag tag, JButton selectButton, JButton deleteButton) {
        tag.setVisible(false);
        deleteButton.setVisible(false);
        updateButtonState(selectButton, true);
        selectButton.setVisible(true);
        updateSelButton();
    }

    /**
     * Opens the comparison display popup to show file comparison.
     * Uses the backend methods for file and metadata comparison based on the selected mode.
     */
    private void openComparisonPopup() {
        try {
            String comparisonResult = "";
            if ("diskDisk".equals(comparisonMode)) {
                comparisonResult = DiskComparison.compareFileMetadata(fileOneTag.getName(), fileTwoTag.getName())
                        ? "Files are identical in size and modification date."
                        : "Files differ in size or modification date.";
            } else if ("catCat".equals(comparisonMode)) {
                comparisonResult = "Comparing file contents:\n";
                FileComparison.compareFiles(fileOneTag.getName(), fileTwoTag.getName());
                // Note: `compareFiles` logs differences directly to console or logger.
            }

            // Open popup with results
            new ComparisonDisplay(fileOneTag.getName(), fileTwoTag.getName()).setVisible(true);
            logger.info("Comparison completed: " + comparisonResult);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during file comparison: " + e.getMessage(), e);
        }
    }

    /**
     * Gets the selected comparison mode.
     *
     * Returns:
     * - comparisonMode: The current comparison mode selected by the user.
     */
    public String getComparisonMode() {
        return comparisonMode;
    }
}
