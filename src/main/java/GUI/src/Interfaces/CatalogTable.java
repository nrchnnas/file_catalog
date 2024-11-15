// CatalogTable.java
//
// This will create the table to display all the files within the catalog
//
// Created by Lalida Krairit, 2ุ October 2024
//

package GUI.src.Interfaces;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import GUI.src.Interfaces.MainFrame;
import utilities.*;
import java.sql.Connection;
import java.util.List;
import utilities.FileRecord;
import utilities.FileCatalog;

public class CatalogTable
{
    JTable table; //reference to the table that contains the files in catalog
    private JScrollPane scrollPane; //reference to the scrollPane attached to the table so it can scroll if overflowed
    MainFrame mainFrame;

    //Arguments: passing the mainFrame
    public CatalogTable(MainFrame mainFrame, JComponent parentComponent)
    {

        this.mainFrame = mainFrame;
        //TO DO: convert from dummy data to real data
        //TO DO: be able to convert strings to integer and date format
        //TO DO: if directory, ext. and size must be empty

        String[] columns = {"Name", "Ext.", "Last Edited Date", "Annotations", "View More"};

        List<FileRecord> catalogRecords = FileCatalog.getAllFiles();
        Object[][] data = new Object[catalogRecords.size()][5];
        for (int i = 0; i < catalogRecords.size(); i++)
        {
            FileRecord record = catalogRecords.get(i);
            data[i][0] = record.getFileName();
            data[i][1] = record.getFileType();
            data[i][2] = record.getModificationDate();
            data[i][3] = record.getAnnotation();
            data[i][4] = "View More";
        }

        //---------------------Table----------------------

        table = new JTable(data, columns);
        table.setRowHeight(20);
        table.setDefaultEditor(Object.class, null); //make it not editable
        table.setFillsViewportHeight(true); // Ensures the table occupies full height in the viewport
        table.setBackground(Color.white);
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setColumnWidths();
        addIcon();

        //----------------View More Button-----------------

        TableColumn viewMoreColumn = table.getColumnModel().getColumn(4);
        viewMoreColumn.setCellRenderer(new ViewMoreButton.ViewButtonRenderer());
        viewMoreColumn.setCellEditor(new ViewMoreButton.ViewButtonEditor(new JButton("View More"), mainFrame, this));

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

    //
    // set the widths of the columns
    //
    private void setColumnWidths()
    {
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        nameColumn.setPreferredWidth(150);

        TableColumn extColumn = table.getColumnModel().getColumn(1);
        extColumn.setPreferredWidth(5);

        TableColumn dateColumn = table.getColumnModel().getColumn(2);
        dateColumn.setPreferredWidth(50);

        TableColumn annotationColumn = table.getColumnModel().getColumn(3);
        annotationColumn.setPreferredWidth(300);

        TableColumn viewMoreColumn = table.getColumnModel().getColumn(4);
        viewMoreColumn.setPreferredWidth(30);
    }

    //
    // Add an icon to the front to tell if it is a file or a directory. It also renders the
    // focus selection similar to changeFocusColor()
    // Returns:
    //      nameCell: the first column of the field that the icon is added to
    //
    private void addIcon()
    {
        ImageIcon documentIcon = new ImageIcon(getClass().getResource("src/main/resources/Document.png"));

        DefaultTableCellRenderer iconRenderer = new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JLabel nameCell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Image scaledImage = documentIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                nameCell.setIcon(new ImageIcon(scaledImage));
                nameCell.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                nameCell.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
                return nameCell;
            }
        };

        table.getColumnModel().getColumn(0).setCellRenderer(iconRenderer);
    }

    //
    // Get file information at specific index
    // Arguments:
    //      -rowIndex: index of row that is selected
    // Returns the file info at specific index
    //
    public FileRecord getFileAt(int rowIndex)
    {
        return FileCatalog.getAllFiles().get(rowIndex);
    }

    // Gets the catalog table that has been instantiated in this class
    // Return:
    //      -table - the table
    public JTable getTable()
    {
        return table;
    }

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

    //refresh table
    public void refreshTable()
    {
        List<FileRecord> catalogRecords = FileCatalog.getAllFiles();
        Object[][] data = new Object[catalogRecords.size()][5];
        for (int i = 0; i < catalogRecords.size(); i++) {
            FileRecord record = catalogRecords.get(i);
            data[i][0] = record.getFileName();
            data[i][1] = record.getFileType();
            data[i][2] = record.getModificationDate();
            data[i][3] = record.getAnnotation();
            data[i][4] = "View More";
        }

        table.setModel(new DefaultTableModel(data, new String[]{"Name", "Ext.", "Last Edited Date", "Annotations", "View More"}));
        TableColumn viewMoreColumn = table.getColumnModel().getColumn(4);
        viewMoreColumn.setCellRenderer(new ViewMoreButton.ViewButtonRenderer());
        viewMoreColumn.setCellEditor(new ViewMoreButton.ViewButtonEditor(new JButton("View More"), mainFrame, this));

        table.revalidate();
        table.repaint();
        setColumnWidths();
        addIcon();
    }
    public void updateTable(List<FileRecord> filteredRecords)
    {

        Object[][] data = new Object[filteredRecords.size()][5];
        for (int i = 0; i < filteredRecords.size(); i++) {
            FileRecord record = filteredRecords.get(i);
            data[i][0] = record.getFileName();
            data[i][1] = record.getFileType();
            data[i][2] = record.getModificationDate();
            data[i][3] = record.getAnnotation();
            data[i][4] = "View More";
        }

        table.setModel(new DefaultTableModel(data, new String[]{"Name", "Ext.", "Last Edited Date", "Annotations", "View More"}));
        TableColumn viewMoreColumn = table.getColumnModel().getColumn(4);
        viewMoreColumn.setCellRenderer(new ViewMoreButton.ViewButtonRenderer());
        viewMoreColumn.setCellEditor(new ViewMoreButton.ViewButtonEditor(new JButton("View More"), mainFrame, this));

        table.revalidate();
        table.repaint();
        setColumnWidths();
        addIcon();
    }

    public static Image getImage(final String pathAndFileName)
    {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}


