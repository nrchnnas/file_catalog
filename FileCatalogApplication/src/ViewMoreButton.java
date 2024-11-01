// ViewMoreButton.java
//
// Contains classes for rendering and editing a "View More" button within a table cell.
//
// Created by Lalida Krairit, 29 October 2024
//

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMoreButton
{

    public static class ViewButtonRenderer extends JButton implements TableCellRenderer
    {
        public ViewButtonRenderer()
        {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    public static class ViewButtonEditor extends DefaultCellEditor
    {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ViewButtonEditor(JButton button)
        {
            super(new JCheckBox());
            this.button = button;
            this.button.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            this.button.setOpaque(true);

            this.button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue()
        {
            // TO DO: if view more is pressed, then lower panel is changed
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing()
        {
            isPushed = false;
            return super.stopCellEditing();
        }

    }
}
