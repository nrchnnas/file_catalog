// FilePanel.java
//
// FilePanel organizes the display for files in the disk, search bar, as well as labels to show current directory
//
// Created by Lalida Krairit, 23 October 2024
//

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FilePanel extends JPanel implements ActionListener
{
    JButton undoButton; //undo directory button
    JButton redoButton; //redo directory button
    MyTextField searchField; //text field for searching files in disk
    MyTextField suffixField; //text field for searching files in disk by searching an extension/suffix
    private final transient FileTable fileTable; //the table containing files in disk

    FilePanel()
    {
        Color LIGHT_GRAY = Color.decode("#E8E8E8");

        ImageIcon UNDO_ICON = new ImageIcon("FileCatalogApplication/src/images/Chevron Left Dark.png");
        ImageIcon REDO_ICON = new ImageIcon("FileCatalogApplication/src/images/Chevron Right Light.png");
        ImageIcon SEARCH_ICON = new ImageIcon("FileCatalogApplication/src/images/Search.png");

        //----------------Current Directory Panel-------------------

        //TO DO: make button dynamic so that it changes from dark to light depending on actions
        undoButton = new JButton();
        undoButton.setFocusable(false);
        undoButton.setIcon(UNDO_ICON);
        undoButton.addActionListener(this);

        redoButton = new JButton();
        redoButton.setFocusable(false);
        redoButton.setIcon(REDO_ICON);
        redoButton.addActionListener(this);

        //TO DO: make currDirLabel dynamic
        JLabel currDirLabel = new JLabel("Disk"); //label for the current directory we are in

        JPanel currDirPanel = new JPanel();
        currDirPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        currDirPanel.add(undoButton);
        currDirPanel.add(redoButton);
        currDirPanel.add(currDirLabel);

        //----------------------Search Panel-----------------------

        JLabel searchImage = new JLabel();
        searchImage.setIcon(SEARCH_ICON);

        searchField = new MyTextField("Search");
        suffixField = new MyTextField("by suffix");

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
        tableScrollPane.setBackground(LIGHT_GRAY);

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
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //TO DO: implement undo and redo functionality

        //TO DO: implement search functionality

        //TO DO: implement searching by suffix functionality
    }

    // Gets the file table that has been instantiated in this class
    // Return:
    //      -filetable - the table
    public FileTable getFileTable()
    {
        return fileTable;
    }

}
