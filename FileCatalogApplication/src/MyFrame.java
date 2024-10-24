// MyFrame.java
//
// MyFrame sets up the frame for the application to layout all the components/buttons/labels.
//
// Created by Lalida Krairit, 23 October 2024
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyFrame extends JFrame implements ActionListener
{
    JButton undoButton;
    JButton redoButton; 

    MyFrame()
    {
        Color LIGHT_GRAY = Color.decode("#E8E8E8");
        Color DARK_GRAY = Color.decode("#CFCFCF");

        ImageIcon UNDO_ICON = new ImageIcon("images/Chevron Left.png");
        ImageIcon REDO_ICON = new ImageIcon("images/Chevron Right.png");

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

        //-----------------------Sub Panels-------------------------

        JPanel upperPanel = new JPanel();
        /*
        upperPanel.add(undoButton);
        upperPanel.add(redoButton);
        upperPanel.add(currDirLabel);
        */
        upperPanel.setPreferredSize(new Dimension(100,55));

        JPanel lowerPanel = new JPanel();
        lowerPanel.setBackground(DARK_GRAY);
        lowerPanel.setPreferredSize(new Dimension(100,175));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //---------------------Sub-Sub Panels-----------------------

        JPanel filePanel = new JPanel();
        filePanel.setBackground(DARK_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4; // 40% of the width
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(filePanel, gbc);

        JPanel catalogPanel = new JPanel();
        catalogPanel.setBackground(Color.WHITE);

        gbc.gridx = 1;
        gbc.weightx = 0.6; // 60% of the width
        gbc.weighty = 1.0;
        centerPanel.add(catalogPanel, gbc);

        //-----------------------Main Panel--------------------------

        this.getContentPane().setBackground(LIGHT_GRAY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double sc_width = screenSize.getWidth();
        double sc_height = screenSize.getHeight();
        int width = (int)(sc_width / (1440/1024));
        int height = (int)(sc_height / (1440/1024));
        this.setSize(width,height); 
 
        this.setLocationRelativeTo(null); //center this
        this.setResizable(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true); 
        this.setLayout(new BorderLayout(10,10));

        this.add(upperPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER); 
        this.add(lowerPanel, BorderLayout.SOUTH);
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
    }   
}

