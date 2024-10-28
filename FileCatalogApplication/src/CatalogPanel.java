// CatalogPanel.java
//
// CatalogPanel organizes the display for the files in the catalog. It allows different searching functionalities. 
//
// Created by Lalida Krairit, 25 October 2024
//

import java.awt.*;
import javax.swing.*;

public class CatalogPanel extends JPanel
{
    MyTextField searchField;
    MyTextField annotationField;
    MyTextField fileTypeField;
    CatalogTable catalogTable;

    CatalogPanel()
    {

        ImageIcon SEARCH_ICON = new ImageIcon("FileCatalogApplication/src/images/Search.png");
        JLabel catalogTitle = new JLabel("Catalog");
        catalogTitle.setHorizontalAlignment(SwingConstants.LEFT);
        catalogTitle.setBackground(Color.WHITE);

        //----------------------Search Panel-----------------------

        JLabel searchImage = new JLabel();
        searchImage.setIcon(SEARCH_ICON);

        searchField = new MyTextField("Search");

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
        searchGbc.weightx = 0.9;
        searchPanel.add(searchField, searchGbc);

        //-------------------Search Option Panel--------------------

        annotationField = new MyTextField("by annotation");
        fileTypeField = new MyTextField("by file Type");

        JPanel searchOptionPanel = new JPanel(new GridBagLayout());
        searchOptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        GridBagConstraints searchOptionGbc = new GridBagConstraints();
        searchOptionGbc.fill = GridBagConstraints.BOTH;

        searchOptionGbc.gridx = 0;
        searchOptionGbc.gridy = 1;
        searchOptionGbc.weightx = 0.4;
        searchOptionPanel.add(annotationField, searchOptionGbc);

        searchOptionGbc.gridx = 1;
        searchOptionGbc.gridy = 1;
        searchOptionGbc.weightx = 0.2;
        searchOptionPanel.add(fileTypeField, searchOptionGbc);

        searchOptionGbc.gridx = 2;
        searchOptionGbc.gridy = 1;
        searchOptionGbc.weightx = 0.4;
        //searchOptionPanel.add(datePicker, searchOptionGbc);

        //---------------------Table Panel-----------------------

        catalogTable = new CatalogTable();
        JScrollPane tableScrollPane = catalogTable.getScrollPane();
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        tableScrollPane.setBackground(Color.WHITE);

        //-----------------------Sub Panels-------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel,BoxLayout.Y_AXIS));
        upperPanel.add(catalogTitle);
        upperPanel.add(searchPanel);
        upperPanel.add(searchOptionPanel);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.setPreferredSize(new Dimension(70, 100));
        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

        //-----------------------Main Panel--------------------------

        this.setLayout(new BorderLayout());
        this.add(upperPanel, BorderLayout.NORTH);
        this.add(lowerPanel, BorderLayout.CENTER);
    }

}
