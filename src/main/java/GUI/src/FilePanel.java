// FilePanel.java
//
// FilePanel organizes the display for files in the disk, search bar, as well as labels to show current directory
//
// Created by Lalida Krairit, 23 October 2024
//

package GUI.src;
import utilities.DirectoryContent;
import utilities.DiskReader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FilePanel extends JPanel implements ActionListener
{
    JButton backButton; //undo directory button
    JButton forwardButton; //redo directory button
    MyTextField searchField; //text field for searching files in disk
    MyTextField suffixField; //text field for searching files in disk by searching an extension/suffix
    private final transient FileTable fileTable; //the table containing files in disk
    private int historyIndex = -1; // to track current position in history
    private List<String> directoryHistory = new ArrayList<>(); // to store history of visited directories
    private JLabel currDirLabel; // Label to display current directory


    FilePanel()
    {
        Color lightGray = Color.decode("#E8E8E8");
        ImageIcon undoIcon = ImageLoader.loadImage("/assets/Chevron Left Dark.png");
        ImageIcon redoIcon = ImageLoader.loadImage("/assets/Chevron Right Dark.png");
        ImageIcon searchIcon = ImageLoader.loadImage("/assets/Search.png");

        //----------------Current Directory Panel-------------------

        backButton = new JButton();
        backButton.setFocusable(false);
        backButton.setIcon(undoIcon);
        backButton.addActionListener(this);
        backButton.setEnabled(false);

        forwardButton = new JButton();
        forwardButton.setFocusable(false);
        forwardButton.setIcon(redoIcon);
        forwardButton.addActionListener(this);
        forwardButton.setEnabled(false);

        String initialDirectory = System.getProperty("user.home");
        currDirLabel = new JLabel(initialDirectory);

        JPanel currDirPanel = new JPanel();
        currDirPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        currDirPanel.add(backButton);
        currDirPanel.add(forwardButton);
        currDirPanel.add(currDirLabel);

        //----------------------Search Panel-----------------------

        JLabel searchImage = new JLabel();
        searchImage.setIcon(searchIcon);

        searchField = new MyTextField("Search");
        suffixField = new MyTextField("by suffix");
        searchField.getDocument().addDocumentListener(new SearchListener());
        suffixField.getDocument().addDocumentListener(new SearchListener());

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.fill = GridBagConstraints.BOTH;

        searchGbc.gridx = 0;
        searchGbc.gridy = 0;
        searchGbc.weightx = 0.02;
        searchPanel.add(searchImage, searchGbc);

        searchGbc.gridx = 1;
        searchGbc.gridy = 0;
        searchGbc.weightx = 0.5;
        searchPanel.add(searchField, searchGbc);

        searchGbc.gridx = 2;
        searchGbc.gridy = 0;
        searchGbc.weightx = 0.1;
        searchPanel.add(suffixField, searchGbc);

        //---------------------Table Panel-----------------------

        fileTable = new FileTable(this);
        JScrollPane tableScrollPane = fileTable.getScrollPane();
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        tableScrollPane.setBackground(lightGray);

        //-----------------------Sub Panels-------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel,BoxLayout.Y_AXIS));
        upperPanel.add(currDirPanel);
        upperPanel.add(searchPanel);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.setPreferredSize(new Dimension(70, 100));
        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

        //-----------------------Main Panel--------------------------

        this.setLayout(new BorderLayout());
        this.add(upperPanel, BorderLayout.NORTH);
        this.add(lowerPanel, BorderLayout.CENTER);

        addToHistory(initialDirectory);
        loadDirectory(initialDirectory);
        fileTable.getTable().getSelectionModel().addListSelectionListener(new DirectorySelectionListener());
    }

    private class SearchListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent e) {
            performSearch();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            performSearch();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            performSearch();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //If back button pressed, update directory based on history
        if (e.getSource() == backButton && historyIndex > 0)
        {
            historyIndex--;
            String prevDir = directoryHistory.get(historyIndex);
            loadDirectory(prevDir);
        //If forward button pressed, get new selected directory to update current directory
        } else if (e.getSource() == forwardButton)
        {
            String selectedDirectory = fileTable.getSelectedDirectoryPath();
            if (selectedDirectory != null)
            {
                addToHistory(selectedDirectory);
                loadDirectory(selectedDirectory);
            }
        }
    }

    //----------------Private Function---------------

    // Updates back and forward button icons and states
    private void updateButtonIcon()
    {
        backButton.setEnabled(historyIndex > 0);
    }

    // Updates back and forward button states
    private void updateButtonStates()
    {
        backButton.setEnabled(historyIndex > 0);
    }

    // Adds a directory to the navigation history and updates button icon based on directory history
    private void addToHistory(String directory)
    {
        // Remove any forward history if new navigation is made
        if (historyIndex < directoryHistory.size() - 1)
        {
            directoryHistory.subList(historyIndex + 1, directoryHistory.size()).clear();
        }
        directoryHistory.add(directory);
        historyIndex++;
        updateButtonIcon();
    }

    // Loads the selected directory and updates the table
    private void loadDirectory(String directory)
    {
        // Reload directory contents in FileTable
        List<DirectoryContent> newRecords = DiskReader.listDirectoryContents(directory);
        fileTable.updateTable(newRecords);
        updateButtonStates();
        // Update current directory label
        updateCurrentDirectoryLabel(directory);
    }

    // Updates the current directory label
    private void updateCurrentDirectoryLabel(String directory)
    {
        currDirLabel.setText(directory);
    }


    // Gets the file table that has been instantiated in this class
    public FileTable getFileTable()
    {
        return fileTable;
    }

    // Inner class to listen for directory selection changes
    private class DirectorySelectionListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            // Ignore extra messages
            if (e.getValueIsAdjusting()) return;

            boolean isDirectorySelected = fileTable.isSelectedDirectory();
            forwardButton.setEnabled(isDirectorySelected);
        }
    }

    //Peforms search of different fields, then allows the table to be filtered accordingly by finding
    //intersection between matches
    //
    private void performSearch()
    {
        String searchTerm = searchField.getInputText().trim().toLowerCase();
        String suffixTerm = suffixField.getInputText().trim().toLowerCase();

        // Get all files in the current directory
        List<DirectoryContent> allFiles = DiskReader.listDirectoryContents(currDirLabel.getText());
        List<DirectoryContent> filteredFiles = new ArrayList<>();

        for (DirectoryContent file : allFiles)
        {
            String fileName = file.getName().toLowerCase();

            // Check if file name matches the search term and suffix
            boolean matchesSearch = searchTerm.isEmpty() || fileName.contains(searchTerm);
            boolean matchesSuffix = suffixTerm.isEmpty() || fileName.endsWith(suffixTerm);

            if (matchesSearch && matchesSuffix)
            {
                filteredFiles.add(file);
            }
        }
        // Update the table with filtered results
        fileTable.updateTable(filteredFiles);
    }
}
