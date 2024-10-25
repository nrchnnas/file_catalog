// FileRowPanel.java
//
// This will create the rows in the file table that can be reusable
//
// Created by Lalida Krairit, 24 October 2024
//

import javax.swing.*;
import java.awt.*;

public class FileRowPanel extends JPanel
{
    private static final int ROW_HEIGHT = 30;

    public FileRowPanel(String fileName, String fileExt, String fileSize, String lastEdited)
    {
        ImageIcon DOCUMENT_ICON = new ImageIcon("images/Document.png");

        this.setLayout(new GridLayout(1, 4));
        //TO DO: if have time, make colours alternate
        this.setBackground(Color.WHITE);

        JPanel iconAndNamePanel = new JPanel();
        iconAndNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        iconAndNamePanel.setBackground(Color.WHITE);
        iconAndNamePanel.setPreferredSize(new Dimension(200, ROW_HEIGHT));

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(DOCUMENT_ICON);
        JLabel nameLabel = new JLabel(fileName);
        nameLabel.setPreferredSize(new Dimension(100, ROW_HEIGHT));

        JLabel extLabel = new JLabel(fileExt);
        extLabel.setOpaque(true);
        extLabel.setBackground(Color.WHITE);
        extLabel.setPreferredSize(new Dimension(50, ROW_HEIGHT));

        JLabel sizeLabel = new JLabel(fileSize);
        sizeLabel.setOpaque(true);
        sizeLabel.setBackground(Color.WHITE);
        sizeLabel.setPreferredSize(new Dimension(50, ROW_HEIGHT));

        JLabel dateLabel = new JLabel(lastEdited);
        dateLabel.setOpaque(true);
        dateLabel.setBackground(Color.WHITE);
        dateLabel.setPreferredSize(new Dimension(100, ROW_HEIGHT));

        iconAndNamePanel.add(imageLabel);
        iconAndNamePanel.add(nameLabel);

        this.add(iconAndNamePanel);
        this.add(extLabel);
        this.add(sizeLabel);
        this.add(dateLabel);

        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
}
