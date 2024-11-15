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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FileTable {
    private JTable table; //reference to the table that contains the files from the disk
    private JScrollPane scrollPane; //reference to the scrollPane attached to the table so it can scroll if overflowed
    private List<DirectoryContent> fileRecords; // Current list of DirectoryContent
    private final FilePanel parentPanel; // Reference to the parent FilePanel for navigation

    public FileTable(FilePanel parentPanel) {
        this.parentPanel = parentPanel;
        String[] columns = {"Name", "Ext.", "Size", "Last Edited Date"};
        this.fileRecords = DiskReader.listDirectoryContents(System.getProperty("user.home")); // Initial directory
        Object[][] data = createTableData(fileRecords);

        //---------------------Table----------------------

        table = new JTable(data, columns);
        table.setRowHeight(20);
        table.setDefaultEditor(Object.class, null); // Make it not editable
        table.setFillsViewportHeight(true); // Ensures the table occupies full height in the viewport
        table.setBackground(Color.WHITE);
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setColumnWidths();
        addIcon();

        //-----------------Row Selection------------------

        // Clear selection when clicking on empty space in the table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (table.rowAtPoint(e.getPoint()) == -1) {
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

        table.getSelectionModel().addListSelectionListener(e ->
        {
            parentPanel.repaint(); // Trigger a repaint if necessary
        });
    }

    // Creates the data array for the table from a list of DirectoryContent
    private Object[][] createTableData(List<DirectoryContent> records)
    {
        Object[][] data = new Object[records.size()][4];
        for (int i = 0; i < records.size(); i++)
        {
            DirectoryContent currentRecord = records.get(i);
            data[i][0] = currentRecord.getName();
            data[i][1] = currentRecord.getExtension();
            data[i][2] = formatSize(currentRecord.getSize());
            data[i][3] = currentRecord.getLastModified();
        }
        return data;
    }

    //
    // Getter of scrollpane so that it can be managed by parent class
    // Returns:
    //      scrollPane: scrollpane for the table
    //
    public JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    //
    // Check if row is selected or not
    // Returns:
    //      true if row is selected and false if it isn't
    //
    public boolean isRowSelected()
    {
        return table.getSelectedRow() != -1;
    }

    // TO DO: might have to get more than file name.

    //
    // Get file name or directory name from selected row
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

    // Get the selected directory's full path
    // Returns:
    //      path if selected row is a directory, otherwise null
    //
    public String getSelectedDirectoryPath()
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1 && fileRecords.get(selectedRow).isDirectory())
        {
            return fileRecords.get(selectedRow).getPath();
        }
        return null;
    }

    //
    // Check if the selected row is a directory
    // Returns:
    //      true if selected row is a directory, false otherwise
    //
    public boolean isSelectedDirectory()
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return fileRecords.get(selectedRow).isDirectory();
        }
        return false;
    }

    //
    // Set the widths of the columns
    //
    private void setColumnWidths()
    {
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        nameColumn.setPreferredWidth(200);

        TableColumn extColumn = table.getColumnModel().getColumn(1);
        extColumn.setPreferredWidth(50);

        TableColumn sizeColumn = table.getColumnModel().getColumn(2);
        sizeColumn.setPreferredWidth(100);

        TableColumn dateColumn = table.getColumnModel().getColumn(3);
        dateColumn.setPreferredWidth(200);
    }

    //
    // Add an icon to the front to tell if it is a file or a directory.
    //
    private void addIcon()
    {
        ImageIcon documentIcon = new ImageIcon("src/main/resources/Document.png");
        ImageIcon folderIcon = new ImageIcon("src/main/resources/Folder.png");

        DefaultTableCellRenderer iconRenderer = new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column)
            {
                JLabel nameCell = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                // Set icon based on if it's a file or directory
                boolean isDirectory = fileRecords.get(row).isDirectory();
                ImageIcon icon = isDirectory ? folderIcon : documentIcon;

                Image scaledImage = icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                nameCell.setIcon(new ImageIcon(scaledImage));

                nameCell.setHorizontalAlignment(SwingConstants.LEFT);
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
            return fileRecords.get(selectedRow).getPath();
        }
        return null;
    }

    // Format the size
    private String formatSize(long size)
    {
        if (size < 1024) return size + " B";
        int exp = (int) (Math.log(size) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1); // K, M, G, T, etc.
        return String.format("%.1f %sB", size / Math.pow(1024, exp), unit);
    }

    // Updates the table with new data
    // Arguments:
    //      newRecords - the new list of DirectoryContent objects to display
    public void updateTable(List<DirectoryContent> newRecords)
    {
        this.fileRecords = newRecords; // Update the internal list
        Object[][] newData = createTableData(newRecords); // Generate the new table data
        DefaultTableModel model = new DefaultTableModel(newData, new String[]{"Name", "Ext.", "Size", "Last Edited Date"}) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false; // Make cells non-editable
            }
        };
        table.setModel(model); // Set the new model
        setColumnWidths();
        addIcon();
    }

    // Getter for fileRecords to be used in listeners
    public List<DirectoryContent> getFileRecords()
    {
        return fileRecords;
    }
}