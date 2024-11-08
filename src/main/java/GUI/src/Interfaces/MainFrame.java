package GUI.src.Interfaces;

// MainFrame.java
//
// MyFrame sets up the frame for the application to layout all the components/buttons/labels.
//
// Created by Lalida Krairit, 23 October 2024
//

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener
{

    JButton compareButton; //compare source files button
    JButton validateButton; //validate catalog button
    JButton addButton; //add to catalog button
    JButton moveFileButton; //move source files button
    JPanel lowerPanel; //the lower panel of the frame
    FilePanel filePanel; //reference to the panel containing the file table

    public MainFrame()
    {
        Color LIGHT_GRAY = Color.decode("#E8E8E8");
        Color DARK_GRAY = Color.decode("#CFCFCF");

        ImageIcon COMPARE_ICON = new ImageIcon("src/main/java/assets/Compare.png");
        ImageIcon VALIDATE_ICON = new ImageIcon("src/main/java/assets/Refresh.png");
        ImageIcon ADD_ICON = new ImageIcon("src/main/java/assets/Add.png");
        ImageIcon MOVE_ICON = new ImageIcon("src/main/java/assets/Move.png");

        //-------------------------Buttons--------------------------

        addButton = new JButton();
        addButton.setFocusable(false);
        addButton.setText("Add to catalog");
        addButton.setIcon(ADD_ICON);
        addButton.addActionListener(this);
        addButton.setEnabled(false);

        moveFileButton = new JButton();
        moveFileButton.setFocusable(false);
        moveFileButton.setText("Move Source files");
        moveFileButton.setIcon(MOVE_ICON);
        moveFileButton.addActionListener(this);
        moveFileButton.setEnabled(false);

        compareButton = new JButton();
        compareButton.setFocusable(false);
        compareButton.setText("Compare Source Files");
        compareButton.setIcon(COMPARE_ICON);
        compareButton.addActionListener(this);

        validateButton = new JButton();
        validateButton.setFocusable(false);
        validateButton.setText("Validate Catalog");
        validateButton.setIcon(VALIDATE_ICON);
        validateButton.addActionListener(this);

        //-----------------------Upper Panel------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        upperPanel.add(addButton);
        upperPanel.add(moveFileButton);
        upperPanel.add(compareButton);
        upperPanel.add(validateButton);

        //----------------------Center Panel------------------------

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        filePanel = new FilePanel();
        //sets the listener which enables the buttons if row is selected from file table
        filePanel.getFileTable().getTable().getSelectionModel().addListSelectionListener(e ->
        {
                boolean rowSelected = filePanel.getFileTable().isRowSelected(); //rowSelected is boolean if row is selected or not
                addButton.setEnabled(rowSelected);
                moveFileButton.setEnabled(rowSelected);
        });
        CatalogPanel catalogPanel = new CatalogPanel(this);

        //setting the layout of the two tables using Grid Bag Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35; // 35% of the width
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(filePanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65; // 65% of the width
        gbc.weighty = 1.0;
        centerPanel.add(catalogPanel, gbc);

        //-----------------------Lower Panel-------------------------

        lowerPanel = new JPanel();
        lowerPanel.setBackground(DARK_GRAY);
        lowerPanel.setPreferredSize(new Dimension(100, 175));

        //-----------------------Main Panel--------------------------

        this.getContentPane().setBackground(LIGHT_GRAY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        int width = (int) (screenWidth / (1440 / 1024));
        int height = (int) (screenHeight / (1440 / 1024));
        this.setSize(width, height);

        this.setLocationRelativeTo(null); //center this
        this.setResizable(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.add(upperPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(lowerPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Adds the add annotation panel to lowerPanel if row is selected
        if (e.getSource() == addButton && filePanel.getFileTable().isRowSelected())
        {
            displayPanel(new AddToCatalogPanel(event -> clearLowerPanel()));

        // Adds the move file panel to lowerPanel if row is selected
        } else if (e.getSource() == moveFileButton && filePanel.getFileTable().isRowSelected())
        {
            // Wrap MoveFilePanel in a final array to allow usage within the lambda
            final MoveFilePanel[] movePanelHolder = new MoveFilePanel[1];
            //MoveFilePanel arguments: 1. closeListener/event, 2. Runnable action for selecting a new directory, 3. table reference
            movePanelHolder[0] = new MoveFilePanel(
                //1
                event -> clearLowerPanel(),
                //2
                () ->
                {
                    int selectedRow = filePanel.getFileTable().getTable().getSelectedRow(); //get row
                    if (selectedRow != -1)
                    {
                        String newDirPath = filePanel.getFileTable().getSelectedPathName(); //get name of directory
                        movePanelHolder[0].setNewDirectory(newDirPath); // Update MoveFilePanel with the selected directory
                    }
                },
                //3
                filePanel.getFileTable()
            );
            displayPanel(movePanelHolder[0]);

        // Adds the compare file panel to lowerPanel
        } else if (e.getSource() == compareButton)
        {
            // Wrap CompareFilePanel in a final array to allow usage within the lambda
            final CompareFilePanel[] comparePanelHolder = new CompareFilePanel[1];
            //CompareFilePanel arguments: 1. closeListener/event, 2. Runnable action for selecting a file, 3. table reference
            comparePanelHolder[0] = new CompareFilePanel(
                //1
                event -> clearLowerPanel(),
                //2
                () ->
                {
                    int selectedRow = filePanel.getFileTable().getTable().getSelectedRow(); //get row
                    if (selectedRow != -1)
                    {
                        String selectedFile = filePanel.getFileTable().getSelectedPathName(); //get name of file
                        if (!comparePanelHolder[0].fileOneTag.isVisible()) //if first tag isn't visible meaning first file has not been selected
                        {
                            comparePanelHolder[0].setFirstTag(selectedFile); //set first file
                        } else //if first tag is visible meaning first file has been selected
                        {
                            comparePanelHolder[0].setSecondTag(selectedFile); //set second file
                        }
                    }
                },
                filePanel.getFileTable()
        );
        displayPanel(comparePanelHolder[0]);
    }
        //TO DO: implement the validate button
    }

    //Displays panels after clicking a specific button and repainting lowerPanel
    //Arguments:
    //      -panel: panel to be displayed
   public void displayPanel(JPanel panel)
    {
        lowerPanel.removeAll();
        lowerPanel.add(panel);
        lowerPanel.revalidate();
        lowerPanel.repaint();
    }

    //Clears components within lower panel
    private void clearLowerPanel()
    {
        lowerPanel.removeAll();
        lowerPanel.revalidate();
        lowerPanel.repaint();
    }
}





