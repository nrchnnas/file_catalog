// CatalogPanel.java
//
// CatalogPanel organizes the display for the files in the catalog. It allows different searching functionalities.
//
// Created by Lalida Krairit, 25 October 2024
//

package GUI.src;
import utilities.FileCatalog;
import utilities.FileRecord;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class CatalogPanel extends JPanel
{
    MyTextField searchField; //text field for searching files in catalog
    MyTextField annotationField; //text field for searching files in catalog using annotation
    MyTextField fileTypeField;  //text field for searching files in catalog using file type
    MyTextField dateField;  //text field for searching files in catalog using date
    private final transient CatalogTable catalogTable; //the table containing files in catalog
    Runnable clearLowerPanel; //reference to the runnable to clear lower panel

    CatalogPanel(MainFrame mainFrame, Runnable clearLowerPanel)
    {

        this.clearLowerPanel = clearLowerPanel;
        Color lightGray = Color.decode("#E8E8E8");

        ImageIcon searchIcon = ImageLoader.loadImage("/assets/Search.png");
        //----------------------Title Panel------------------------

        JLabel catalogTitle = new JLabel("Catalog");
        catalogTitle.setFont(new Font("", Font.PLAIN, 20));
        catalogTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(catalogTitle);

        //----------------------Search Panel-----------------------

        JLabel searchImage = new JLabel();
        searchImage.setIcon(searchIcon);

        searchField = new MyTextField("Search");
        searchField.setBackground(lightGray);
        searchField.getDocument().addDocumentListener(new SearchListener());
        searchField.addExternalClickListener(this);

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
        annotationField.setBackground(lightGray);
        fileTypeField = new MyTextField("by file Type");
        fileTypeField.setBackground(lightGray);
        dateField = new MyTextField("by Last edited date YYYY-MM-DD");
        dateField.setBackground(lightGray);

        annotationField.getDocument().addDocumentListener(new SearchListener());
        fileTypeField.getDocument().addDocumentListener(new SearchListener());
        dateField.getDocument().addDocumentListener(new SearchListener());

        annotationField.addExternalClickListener(this);
        fileTypeField.addExternalClickListener(this);
        dateField.addExternalClickListener(this);

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

        catalogTable = new CatalogTable(mainFrame, this, clearLowerPanel);
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

    //Adds document Listener to table so that it can be updated based on search
    //
    private class SearchListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent e) { performSearch(); }
        @Override
        public void removeUpdate(DocumentEvent e) { performSearch(); }
        @Override
        public void changedUpdate(DocumentEvent e) { performSearch(); }
    }

    //----------------Private Function---------------

    //Peforms search of different fields, then allows the table to be filtered accordingly by finding
    //intersection between matches
    //
    private void performSearch()
    {
        String searchTerm = searchField.getInputText().trim().toLowerCase();
        String fileType = fileTypeField.getInputText().trim().toLowerCase();
        String annotation = annotationField.getInputText().trim().toLowerCase();
        String date = dateField.getInputText().trim().toLowerCase();
        List<FileRecord> allFiles = FileCatalog.getAllFiles();
        List<FileRecord> filteredFiles = new ArrayList<>();
        for (FileRecord file : allFiles)
        {
            String fileName = file.getFileName().toLowerCase();
            String fileExtension = file.getFileType().toLowerCase();

            boolean matchesSearch = searchTerm.isEmpty()|| fileName.contains(searchTerm);
            boolean matchesType = fileType.isEmpty() || fileExtension.equals(fileType);
            boolean matchesDate = date.isEmpty() || file.getModificationDate().contains(date);
            boolean matchesAnnotation = annotation.isEmpty() || (file.getAnnotation() != null && file.getAnnotation().toLowerCase().contains(annotation));

            if (matchesSearch && matchesType && matchesDate && matchesAnnotation)
            {
                filteredFiles.add(file);
            }
        }
        catalogTable.updateFilteredTable(filteredFiles);
    }

    //----------------Public Function---------------

    // Gets the catalog table that has been instantiated in this class
    // Returns catalogTable
    public CatalogTable getCatalogTable()
    {
        return catalogTable;
    }

}
