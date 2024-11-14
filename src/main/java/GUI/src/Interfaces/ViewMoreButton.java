// ViewMoreButton.java
//
// Contains classes for rendering and editing a "View More" button within a table cell.
//
// Created by Lalida Krairit, 29 October 2024
//

package GUI.src.Interfaces;
import utilities.FileCatalog;
import utilities.FileRecord;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewMoreButton
{
    public static class ViewButtonRenderer extends JButton implements TableCellRenderer
    {
        //make button opaque
        public ViewButtonRenderer()
        {
            setOpaque(true);
        }

        //get rendered component and set its text
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            if(value == null)
            {
                setText("");
            }
            else
            {
                setText(value.toString());
            }
            return this;
        }
    }

    public static class ViewButtonEditor extends DefaultCellEditor
    {
        private JButton button; //reference to view more button
        private String label; //label for the button
        private CatalogTable catalogTable; //reference to the catalog table
        private MainFrame mainFrame; //reference to main frame

        public ViewButtonEditor(JButton button, MainFrame mainFrame, CatalogTable catalogTable)
        {
            super(new JCheckBox());
            this.button = button;
            this.mainFrame = mainFrame;
            this.catalogTable = catalogTable;
            this.button.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            this.button.setOpaque(true);

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

        //get cell editor component and return the button
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            if(value == null)
            {
                label = "";
            }
            else
            {
                label = value.toString();
            }
            button.setText(label);
            return button;
        }

        //Returns value from cell
        @Override
        public Object getCellEditorValue()
        {
            return label;
        }

        //Stops user from editing cell and returning it
        @Override
        public boolean stopCellEditing()
        {
            return super.stopCellEditing();
        }

        //Opens view more display of selected file
        private void openViewMoreDisplay()
        {
            int selectedRow = catalogTable.table.getEditingRow();
            if (selectedRow != -1)
            {
                try
                {
                    List<FileRecord> catalogRecords = FileCatalog.getAllFiles();
                    FileRecord record = catalogRecords.get(selectedRow);
                    mainFrame.displayPanel(new ViewMorePanel(record));
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(mainFrame, "Error loading file details: " + ex.getMessage());
                }
            } else
            {
                JOptionPane.showMessageDialog(mainFrame, "Please select a row from catalog first.");
            }
        }

    }
}

