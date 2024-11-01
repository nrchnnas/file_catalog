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

    MainFrame()
    {
        Color LIGHT_GRAY = Color.decode("#E8E8E8");
        Color DARK_GRAY = Color.decode("#CFCFCF");

        ImageIcon COMPARE_ICON = new ImageIcon("FileCatalogApplication/src/images/Compare.png");
        ImageIcon VALIDATE_ICON = new ImageIcon("FileCatalogApplication/src/images/Refresh.png");
        ImageIcon ADD_ICON = new ImageIcon("FileCatalogApplication/src/images/Add.png");
        ImageIcon MOVE_ICON = new ImageIcon("FileCatalogApplication/src/images/Move.png");

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
        filePanel.getFileTable().getTable().getSelectionModel().addListSelectionListener(e ->
                addButton.setEnabled(filePanel.getFileTable().isRowSelected()));

        CatalogPanel catalogPanel = new CatalogPanel();

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

        lowerPanel  = new JPanel();
        lowerPanel.setBackground(DARK_GRAY);
        lowerPanel.setPreferredSize(new Dimension(100,175));

        //-----------------------Main Panel--------------------------

        this.getContentPane().setBackground(LIGHT_GRAY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        int width = (int)(screenWidth / (double)(1440/1024));
        int height = (int)(screenHeight / (double)(1440/1024));
        this.setSize(width,height);

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
        // adds the add annotation panel to lowerPanel if row is selected
        if (e.getSource() == addButton)
        {
            if (filePanel.getFileTable().isRowSelected())
            {
                lowerPanel.removeAll();
                AddToCatalogPanel addPanel = new AddToCatalogPanel(event ->
                {
                    lowerPanel.removeAll();
                    lowerPanel.revalidate();
                    lowerPanel.repaint();
                });

                lowerPanel.add(addPanel);
                lowerPanel.revalidate();
                lowerPanel.repaint();
            } else
            {
                JOptionPane.showMessageDialog(this, "Please select a file from the left table.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == moveFileButton)
        {
            // NEXT TO DO
        }

        //TO DO: implement the other functions

    }

}

