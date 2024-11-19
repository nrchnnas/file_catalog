// CatalogTable.java
//
// This will create the table to display all the files within the catalog
//
// Created by Lalida Krairit, 2 October 2024
//

package GUI.src;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import utilities.FileRecord;
import utilities.FileCatalog;

public class CatalogTable
{
    JTable table; //reference to the table that contains the files in catalog
    private JScrollPane scrollPane; //reference to the scrollPane attached to the table so it can scroll if overflowed
    MainFrame mainFrame; //reference to mainFrame
    private ViewMorePanel currentViewMorePanel; // Current ViewMorePanel being displayed
    Runnable clearLowerPanel; //clear lower panel upon updating

    //Arguments: passing the mainFrame, parentComponent which is the catalog panel
    public CatalogTable(MainFrame mainFrame, JComponent parentComponent, Runnable clearLowerPanel)
    {
        this.mainFrame = mainFrame;
        this.clearLowerPanel = clearLowerPanel;

        String[] columns = {"Name", "Ext.", "Last Edited Date", "Annotations", "View More"};
        Object[][] catalogRecords = getTableData(FileCatalog.getAllFiles());

        //---------------------Table----------------------

        table = new JTable(catalogRecords, columns);
        table.setRowHeight(20);
        table.setDefaultEditor(Object.class, null); //make it not editable
        table.setFillsViewportHeight(true); // Ensures the table occupies full height in the viewport
        table.setBackground(Color.white);
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addViewMoreButton();
        setColumnWidths();
        addIcon();

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

    //----------------Private Function---------------

    // Set the widths of the columns
    //
    private void setColumnWidths()
    {
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(5);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(250);
        table.getColumnModel().getColumn(4).setPreferredWidth(30);
    }


    // Add an icon to the front to tell if it is a file or a directory.
    //
    private void addIcon()
    {
        ImageIcon documentIcon = ImageLoader.loadImage("/assets/Document.png");

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

    // Populates the table data from the list of catalog records
    // Returns table data
    //
    private Object[][] getTableData(List<FileRecord> records)
    {
        Object[][] data = new Object[records.size()][5];
        for (int i = 0; i < records.size(); i++)
        {
            FileRecord record = records.get(i);
            data[i][0] = record.getFileName();
            data[i][1] = record.getFileType();
            data[i][2] = record.getModificationDate();
            data[i][3] = record.getAnnotation();
            data[i][4] = "View More";
        }
        return data;
    }

    //Adds the view more button and renders it
    //
    private void addViewMoreButton()
    {
        TableColumn viewMoreColumn = table.getColumnModel().getColumn(4);
        viewMoreColumn.setCellRenderer(new ViewButtonRenderer());
        viewMoreColumn.setCellEditor(new ViewButtonEditor(new JButton("View More"), this, clearLowerPanel));
    }

    //----------------Public Function---------------

    // Getter of scrollpane so that it can be managed my parent class
    // Returns scrollpane
    //
    public JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    // Check if row is selected or not
    // Returns true if row is selected and false if it isn't
    //
    public boolean isRowSelected()
    {
        return table.getSelectedRow() != -1;
    }

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
    // Returns table
    //
    public JTable getTable()
    {
        return table;
    }

    // Get file name or directory name from selected row
    // Returns path name value, otherwise null if row is not selected
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

    // Refreshes table everytime file is added to catalog, file is deleted, or annotations are edited
    //
    public void refreshTable()
    {
        Object[][] catalogRecords = getTableData(FileCatalog.getAllFiles());
        table.setModel(new DefaultTableModel(catalogRecords, new String[]{"Name", "Ext.", "Last Edited Date", "Annotations", "View More"}));

        addViewMoreButton();
        setColumnWidths();
        addIcon();
        table.revalidate();
        table.repaint();
    }

    // Update catalog table so it filters based on the search
    //
    public void updateFilteredTable(List<FileRecord> filteredRecords)
    {

        Object[][] data = new Object[filteredRecords.size()][5];
        for (int i = 0; i < filteredRecords.size(); i++)
        {
            FileRecord record = filteredRecords.get(i);
            data[i][0] = record.getFileName();
            data[i][1] = record.getFileType();
            data[i][2] = record.getModificationDate();
            data[i][3] = record.getAnnotation();
            data[i][4] = "View More";
        }

        table.setModel(new DefaultTableModel(data, new String[]{"Name", "Ext.", "Last Edited Date", "Annotations", "View More"}));

        addViewMoreButton();
        setColumnWidths();
        addIcon();
        table.revalidate();
        table.repaint();
    }

    //Sets up the view more button render
    private static class ViewButtonRenderer extends JButton implements TableCellRenderer
    {
        public ViewButtonRenderer()
        {
            setOpaque(true);
        }

        //get rendered component and set its text
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    // Handles the "View More" button click events
    private class ViewButtonEditor extends DefaultCellEditor
    {
        private JButton button; //reference to view more button
        private String label; //label for the button
        private CatalogTable catalogTable; //reference to the catalog table
        private Runnable clearLowerPanel; //reference to clearLowerPanel

        public ViewButtonEditor(JButton button, CatalogTable catalogTable, Runnable clearLowerPanel)
        {
            super(new JCheckBox());
            this.button = button;
            this.catalogTable = catalogTable;
            this.clearLowerPanel = clearLowerPanel;
            this.button.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

            this.button.addActionListener(new ActionListener()
            {
                //when button is clicked, it opens view more display
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    openViewMoreDisplay();
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            label = value == null ? "" : value.toString();
            button.setText(label);
            return button;
        }

        //Returns value from cell
        @Override
        public Object getCellEditorValue()
        {
            return label;
        }

        //----------------Public Function---------------

        //Opens the view more panel on the lower panel
        private void openViewMoreDisplay()
        {
            int selectedRow = table.getEditingRow();
            if (selectedRow != -1)
            {
                FileRecord fileInfo = catalogTable.getFileAt(selectedRow);
                System.out.println("Selected Row: " + selectedRow);
                System.out.println("File Info: " + fileInfo.getFileName());
                System.out.println("Creating new ViewMorePanel");
                // Create a new panel and display it
                currentViewMorePanel = new ViewMorePanel(
                        fileInfo,
                        catalogTable::refreshTable,
                        () -> clearLowerPanel.run()
                );
                catalogTable.mainFrame.displayPanel(currentViewMorePanel);
            }
        }
    }
}


