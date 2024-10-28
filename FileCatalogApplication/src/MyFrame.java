// MyFrame.java
//
// MyFrame sets up the frame for the application to layout all the components/buttons/labels.
//
// Created by Lalida Krairit, 23 October 2024
//

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener
{

    JButton compareButton;
    JButton validateButton;

    MyFrame()
    {
        Color LIGHT_GRAY = Color.decode("#E8E8E8");
        Color DARK_GRAY = Color.decode("#CFCFCF");

        ImageIcon COMPARE_ICON = new ImageIcon("FileCatalogApplication/src/images/Compare.png");
        ImageIcon VALIDATE_ICON = new ImageIcon("FileCatalogApplication/src/images/Reboot.png");

        //-----------------------Upper Panel------------------------

        compareButton = new JButton();
        compareButton.setFocusable(false);
        compareButton.setText("Compare Source Files: ");
        compareButton.setIcon(COMPARE_ICON);
        compareButton.addActionListener(this);

        validateButton = new JButton();
        validateButton.setFocusable(false);
        validateButton.setText("Validate Catalog: ");
        validateButton.setIcon(VALIDATE_ICON);
        validateButton.addActionListener(this);

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        upperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        upperPanel.setPreferredSize(new Dimension(100,70));
        upperPanel.add(compareButton);
        upperPanel.add(validateButton);

        //----------------------Center Panel------------------------

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        FilePanel filePanel = new FilePanel();
        CatalogPanel catalogPanel = new CatalogPanel();

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

        //TO DO: dynamic according to button selections
        JPanel lowerPanel = new JPanel();
        lowerPanel.setBackground(DARK_GRAY);
        lowerPanel.setPreferredSize(new Dimension(100,175));
        
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
        this.setLayout(new BorderLayout());

        this.add(upperPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER); 
        this.add(lowerPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //TO DO: implement compare and validate logic
        if (e.getSource() == compareButton)
        {
            System.out.println(compareButton.getText());
        }
        else if (e.getSource() == validateButton)
        {
            System.out.println(validateButton.getText());
        }
    }
}

