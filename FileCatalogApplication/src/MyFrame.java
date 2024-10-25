// MyFrame.java
//
// MyFrame sets up the frame for the application to layout all the components/buttons/labels.
//
// Created by Lalida Krairit, 23 October 2024
//

import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame
{

    MyFrame()
    {
        Color LIGHT_GRAY = Color.decode("#E8E8E8");
        Color DARK_GRAY = Color.decode("#CFCFCF");

        //-----------------------Sub Panels-------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setPreferredSize(new Dimension(100,55));

        JPanel lowerPanel = new JPanel();
        lowerPanel.setBackground(DARK_GRAY);
        lowerPanel.setPreferredSize(new Dimension(100,175));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //----------------------Center Panel------------------------

        FilePanel filePanel = new FilePanel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4; // 40% of the width
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(filePanel, gbc);

        CatalogPanel catalogPanel = new CatalogPanel();
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
        this.setLayout(new BorderLayout(5,5));

        this.add(upperPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER); 
        this.add(lowerPanel, BorderLayout.SOUTH);
    }
}

