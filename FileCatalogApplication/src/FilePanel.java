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
import javax.swing.table.DefaultTableModel;

public class FilePanel extends JPanel implements ActionListener
{
    JButton undoButton;
    JButton redoButton;
    MyTextField searchField;
    MyTextField suffixField;
    FileTable fileTable;
    
    FilePanel()
    {

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

        fileTable = new FileTable();
        JScrollPane tableScrollPane = fileTable.getScrollPane();

        //-----------------------Sub Panels-------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel,BoxLayout.Y_AXIS));
        upperPanel.add(currDirPanel);
        upperPanel.add(searchPanel);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
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

}
