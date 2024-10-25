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
    JButton undoButton;
    JButton redoButton;
    MyTextField searchField;
    MyTextField suffixField;
    
    FilePanel()
    {

        Color DARK_GRAY = Color.decode("#CFCFCF");
        Color ACCENT = Color.decode("#3E3E3E");

        ImageIcon UNDO_ICON = new ImageIcon("images/Chevron Left.png");
        ImageIcon REDO_ICON = new ImageIcon("images/Chevron Right.png");
        ImageIcon SEARCH_ICON = new ImageIcon("images/Search.png");

        //----------------Current Directory Panel-------------------

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
        searchField.setPreferredSize(new Dimension(350, 30));

        suffixField = new MyTextField("by suffix");
        suffixField.setPreferredSize(new Dimension(75, 30));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        searchPanel.add(searchImage);
        searchPanel.add(searchField);
        searchPanel.add(suffixField);

        //---------------------Header Panel-----------------------

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(ACCENT);

        JLabel nameHeader = createHeaderLabel("Name");
        JLabel extHeader = createHeaderLabel("Ext.");
        JLabel sizeHeader = createHeaderLabel("Size");
        JLabel dateHeader = createHeaderLabel("Last Edited Date");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        headerPanel.add(nameHeader, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.1;
        headerPanel.add(extHeader, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.2;
        headerPanel.add(sizeHeader, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.4;
        headerPanel.add(dateHeader, gbc);

        //------------------------Row Panel--------------------------

        //TO DO: make rows dynamic based on the number of files in the directory
        //TO DO: be able to convert strings to integer and date format
        JPanel rowPanel = new JPanel(new GridBagLayout());
        rowPanel.setBackground(DARK_GRAY);

        addRow(rowPanel, "Program", "C", "176B", "21.08.2019 17:00", 0);
        addRow(rowPanel, "Document", "txt", "5KB", "10.11.2020 09:00", 1);
        addRow(rowPanel, "Image", "png", "1.2MB", "05.06.2021 15:30", 2);
        addRow(rowPanel, "Music", "mp3", "3MB", "12.12.2022 12:00", 3);

        //-----------------------Sub Panels-------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel,BoxLayout.Y_AXIS));
        upperPanel.add(currDirPanel);
        upperPanel.add(searchPanel);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.add(headerPanel, BorderLayout.NORTH);
        lowerPanel.add(rowPanel, BorderLayout.CENTER);

        //-----------------------Main Panel--------------------------

        this.setLayout(new BorderLayout());
        this.add(upperPanel, BorderLayout.NORTH);
        this.add(lowerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //TO DO: implement undo and redo functionality
        if (e.getSource() == undoButton) 
        {
            System.out.println("Undo button clicked");
        }
        else if (e.getSource() == redoButton) 
        {
            System.out.println("Redo button clicked");

        }
        //TO DO: implement search functionality
        
        //TO DO: implement searching by suffix functionality
    }

    //
    // Creates header label
    // Arguments:
    //      text: the text for the label
    // Returns:
    //      the header label
    //
    private JLabel createHeaderLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.LEFT);
        return label;
    }

    //
    // Adds the row to the grid layout
    // Arguments:
    //      panel: panel for the row to be added to
    //      fileName: file name
    //      fileExt: file extension
    //      fileSize: file size
    //      lastEdited: last edited date
    //      rowIndex: current index for rows
    //
    private void addRow(JPanel panel, String fileName, String fileExt, String fileSize, String lastEdited, int rowIndex)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = rowIndex;
        gbc.weightx = 0.3;
        panel.add(new JLabel(fileName), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.1;
        panel.add(new JLabel(fileExt), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.2;
        panel.add(new JLabel(fileSize), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.4;
        panel.add(new JLabel(lastEdited), gbc);
    }
    
}
