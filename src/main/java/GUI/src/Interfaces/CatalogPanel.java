// CatalogPanel.java
//
// CatalogPanel organizes the display for the files in the catalog. It allows different searching functionalities.
//
// Created by Lalida Krairit, 25 October 2024
//

package GUI.src.Interfaces;
import java.awt.*;
import javax.swing.*;

public class CatalogPanel extends JPanel
{
    MyTextField searchField; //text field for searching files in catalog
    MyTextField annotationField; //text field for searching files in catalog using annotation
    MyTextField fileTypeField;  //text field for searching files in catalog using file type
    MyTextField dateField;  //text field for searching files in catalog using date
    private final transient CatalogTable catalogTable; //the table containing files in catalog

    CatalogPanel(MainFrame mainFrame)
    {

        Color LIGHT_GRAY = Color.decode("#E8E8E8");
        ImageIcon SEARCH_ICON = new ImageIcon("src/main/java/assets/Search.png");

        //----------------------Title Panel------------------------

        JLabel catalogTitle = new JLabel("Catalog");
        catalogTitle.setFont(new Font("", Font.PLAIN, 20));
        catalogTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(catalogTitle);

        //----------------------Search Panel-----------------------

        JLabel searchImage = new JLabel();
        searchImage.setIcon(SEARCH_ICON);

        searchField = new MyTextField("Search");
        searchField.setBackground(LIGHT_GRAY);

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setOpaque(true);
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
        annotationField.setBackground(LIGHT_GRAY);
        fileTypeField = new MyTextField("by file Type");
        fileTypeField.setBackground(LIGHT_GRAY);
        dateField = new MyTextField("by Last edited date");
        dateField.setBackground(LIGHT_GRAY);

        JPanel searchOptionPanel = new JPanel(new GridBagLayout());
        searchOptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        searchOptionPanel.setBackground(Color.WHITE);
        searchOptionPanel.setOpaque(true);
        GridBagConstraints searchOptionGbc = new GridBagConstraints();
        searchOptionGbc.fill = GridBagConstraints.BOTH;

        searchOptionGbc.gridx = 0;
        searchOptionGbc.gridy = 1;
        searchOptionGbc.weightx = 0.6;
        searchOptionPanel.add(annotationField, searchOptionGbc);

        searchOptionGbc.gridx = 1;
        searchOptionGbc.gridy = 1;
        searchOptionGbc.weightx = 0.2;
        searchOptionPanel.add(fileTypeField, searchOptionGbc);

        searchOptionGbc.gridx = 2;
        searchOptionGbc.gridy = 1;
        searchOptionGbc.weightx = 0.2;
        searchOptionPanel.add(dateField, searchOptionGbc);

        //---------------------Table Panel-----------------------

        catalogTable = new CatalogTable(mainFrame);
        JScrollPane tableScrollPane = catalogTable.getScrollPane();
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        tableScrollPane.setBackground(Color.WHITE);

        //-----------------------Sub Panels-------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel,BoxLayout.Y_AXIS));
        upperPanel.add(titlePanel);
        upperPanel.add(searchPanel);
        upperPanel.add(searchOptionPanel);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.setPreferredSize(new Dimension(70, 100));
        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

        //-----------------------Main Panel--------------------------

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        this.add(upperPanel, BorderLayout.NORTH);
        this.add(lowerPanel, BorderLayout.CENTER);
    }
}
