// FileTable.java
//
// This will create the table to display all the files in the disk
//
// Created by Lalida Krairit, 25 October 2024
//

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FileTable
{
    private JTable table; //reference to the table that contains the files from the disk
    private JScrollPane scrollPane; //reference to the scrollPane attached to the table so it can
    // scroll if overflowed

    public class LibraryConstants
    {
        public static final String PROGRAM_NAME = "Program";
        public static final String LANGUAGE = "C";
        public static final String SIZE = "176B";
        public static final String LAST_UPDATED = "21.08.2019 17:00";
    }

    public FileTable(JComponent parentComponent)
    {

        //TO DO: convert from dummy data to real data
        //TO DO: be able to convert strings to integer and date format
        //TO DO: if directory, ext. and size must be empty

        String[] columns = {"Name", "Ext.", "Size", "Last Edited Date"};
        Object[][] data =
                {
                        {FileTable.LibraryConstants.PROGRAM_NAME, FileTable.LibraryConstants.LANGUAGE, FileTable.LibraryConstants.SIZE, FileTable.LibraryConstants.LAST_UPDATED},
                        {FileTable.LibraryConstants.PROGRAM_NAME, FileTable.LibraryConstants.LANGUAGE, FileTable.LibraryConstants.SIZE, FileTable.LibraryConstants.LAST_UPDATED},
                        {FileTable.LibraryConstants.PROGRAM_NAME, FileTable.LibraryConstants.LANGUAGE, FileTable.LibraryConstants.SIZE, FileTable.LibraryConstants.LAST_UPDATED},
                        {FileTable.LibraryConstants.PROGRAM_NAME, FileTable.LibraryConstants.LANGUAGE, FileTable.LibraryConstants.SIZE, FileTable.LibraryConstants.LAST_UPDATED},
                        {FileTable.LibraryConstants.PROGRAM_NAME, FileTable.LibraryConstants.LANGUAGE, FileTable.LibraryConstants.SIZE, FileTable.LibraryConstants.LAST_UPDATED},
                };

        //---------------------Table----------------------

        table = new JTable(data, columns);
        table.setRowHeight(20);
        table.setDefaultEditor(Object.class, null); //make it not editable
        table.setFillsViewportHeight(true); // Ensures the table occupies full height in the viewport
        table.setBackground(Color.decode("#CFCFCF"));
        table.setFocusable(false);

        setColumnWidths();
        //TO DO: if its directory, add file icon
        addIcon();
        changeFocusColor();

        //-----------------Row Selection------------------

        // Adds a selection listener so that we can get values of each row selection
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    //TO DO: handle row selection logic
                }
            }
        });

        // Clear table selection if click is outside the table
        // Arguments:
        //      -e: mouse event
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
    // get file name from selected row
    // Returns:
    //      file name value, otherwise null if row is not selected
    //
    public String getSelectedFileName()
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
    // Customize the focus color so that when field is selected, it is highlighted in a different color
    // Returns:
    //      cell: the cell which is being selected/focused
    //
    private void changeFocusColor()
    {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected)
                {
                    cell.setBackground(Color.LIGHT_GRAY);
                    cell.setForeground(Color.BLACK);
                } else
                {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }
                setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                return cell;
            }
        });
    }

    //
    // Add an icon to the front to tell if it is a file or a directory. It also renders the
    // focus selection similar to changeFocusColor()
    // Returns:
    //      nameCell: the first column of the field that the icon is added to
    //
    private void addIcon()
    {
        ImageIcon documentIcon = new ImageIcon("FileCatalogApplication/src/images/Document.png");
        ImageIcon folderIcon = new ImageIcon("FileCatalogApplication/src/images/Folder.png");

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

                if (isSelected)
                {
                    nameCell.setBackground(Color.LIGHT_GRAY);
                    nameCell.setForeground(Color.BLACK);
                } else
                {
                    nameCell.setBackground(Color.WHITE);
                    nameCell.setForeground(Color.BLACK);
                }

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
}
