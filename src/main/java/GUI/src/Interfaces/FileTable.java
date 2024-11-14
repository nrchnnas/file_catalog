// FileTable.java
//
// This will create the table to display all the files in the disk
//
// Created by Lalida Krairit, 25 October 2024
//

package GUI.src.Interfaces;
import utilities.DirectoryContent;
import utilities.DiskReader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class FileTable
{
    private File currentDirectory = new File(System.getProperty("user.home"));
    private JTable table; //reference to the table that contains the files from the disk
    private JScrollPane scrollPane; //reference to the scrollPane attached to the table so it can scroll if overflowed

    public class LibraryConstants
    {
        public static final String PROGRAM_NAME = "Program";
        public static final String LANGUAGE = "C";
        public static final String SIZE = "176B";
        public static final String LAST_UPDATED = "21.08.2019 17:00";
    }

    public FileTable(JComponent parentComponent)
    {
        //TO DO: get only source files
        //TO DO: if directory, ext. and size must be empty
        List<DirectoryContent> fileRecords = DiskReader.listDirectoryContents(currentDirectory.getAbsolutePath());
        String[] columns = {"Name", "Ext.", "Size", "Last Edited Date"};
        Object[][] data = new Object[fileRecords.size()][4];
        for (int i = 0; i < fileRecords.size(); i++){
            DirectoryContent currentRecord = fileRecords.get(i);
            data[i][0] = currentRecord.getName();
            data[i][1] = currentRecord.getExtension();
            data[i][2] = formatSize(currentRecord.getSize());
            data[i][3] = currentRecord.getLastModified();
        }

        //---------------------Table----------------------

        table = new JTable(data, columns);
        table.setRowHeight(20);
        table.setDefaultEditor(Object.class, null); //make it not editable
        table.setFillsViewportHeight(true); // Ensures the table occupies full height in the viewport
        table.setBackground(Color.WHITE);
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        setColumnWidths();
        addIcon();

        //-----------------Row Selection------------------

        // Clear selection when clicking on empty space in the table
        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {

                if (table.rowAtPoint(e.getPoint()) == -1)
                {
                    table.clearSelection();
                }
            }
        });

        // Clear table selection if click is outside the table
        parentComponent.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getSource() != table)
                {
                    table.clearSelection();
                }

            }
        });

        //------------------Table Header-------------------

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.decode("#3E3E3E"));
        tableHeader.setForeground(Color.white);
        tableHeader.setReorderingAllowed(false);

        //------------------Scroll Pane-------------------

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


    }

    
    //
    // Getter of scrollpane so that it can be managed my parent class
    // Returns:
    //      scrollPane: scrollpane for the table
    //
    public JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    //
    // check if row is selected or not
    // Returns:
    //      true if row is selected and false if it isn't
    //
    public boolean isRowSelected()
    {
        return table.getSelectedRow() != -1;
    }

    //TO DO: might have to get more than file name.

    //
    // get file name or directory name from selected row
    // Returns:
    //      name value, otherwise null if row is not selected
    //
    public String getSelectedPathName()
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1)
        {
            return table.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }

    //
    // set the widths of the columns
    //
    private void setColumnWidths()
    {
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        nameColumn.setPreferredWidth(100);

        TableColumn extColumn = table.getColumnModel().getColumn(1);
        extColumn.setPreferredWidth(5);

        TableColumn sizeColumn = table.getColumnModel().getColumn(2);
        sizeColumn.setPreferredWidth(5);

        TableColumn dateColumn = table.getColumnModel().getColumn(3);
        dateColumn.setPreferredWidth(50);
    }


    //
    // Add an icon to the front to tell if it is a file or a directory. It also renders the
    // focus selection similar to changeFocusColor()
    // Returns:
    //      nameCell: the first column of the field that the icon is added to
    //
    private void addIcon()
    {
        ImageIcon documentIcon = new ImageIcon("src/main/java/assets/Document.png");
        ImageIcon folderIcon = new ImageIcon("src/main/java/assets/Folder.png");

        DefaultTableCellRenderer iconRenderer = new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JLabel nameCell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Set icon based on if it's a file or directory
                String ext = table.getValueAt(row, 1).toString();
                ImageIcon icon = ext.equals("dir") ? folderIcon : documentIcon;

                Image scaledImage = icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                nameCell.setIcon(new ImageIcon(scaledImage));

                nameCell.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                nameCell.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
                return nameCell;
            }
        };

        table.getColumnModel().getColumn(0).setCellRenderer(iconRenderer);
    }

    // Gets the file table that has been instantiated in this class
    // Return:
    //      -table - the table
    public JTable getTable()
    {
        return table;
    }


    // Retrieves the full path of the selected file in the table
    // Returns path if found, otherwise null
    public String getSelectedFilePath()
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1)
        {
            List<DirectoryContent> fileRecords = DiskReader.listDirectoryContents(currentDirectory.getAbsolutePath());
            return fileRecords.get(selectedRow).getPath();
        }
        return null;
    }

    //format the size
    private String formatSize(long size)
    {
        if (size < 1024) return size + " B";
        int exp = (int) (Math.log(size) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1); // K, M, G, T, etc.
        return String.format("%.1f %sB", size / Math.pow(1024, exp), unit);
    }
}
