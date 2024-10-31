// AddToCatalogPanel.java
//
// The panel that will be displayed after the add to catalog button is clicked
// and handles the logic when a file is added to the catalog along with its annotation
//
// Created by Lalida Krairit, 30 October 2024
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AddToCatalogPanel extends JPanel
{
    JButton cancelButton; //button to cancel
    JButton addButton; //button to add to catalog
    JTextArea addAnnotationField; //text field for annotation
    private final String placeholderText = "Add an annotation: "; //placeholder text inside the text field

    public AddToCatalogPanel(ActionListener closeListener)
    {
        setLayout(new FlowLayout());
        setOpaque(false);

        addAnnotationField = new JTextArea(placeholderText);
        addAnnotationField.setLineWrap(true);
        addAnnotationField.setWrapStyleWord(true);
        addAnnotationField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        addAnnotationField.setPreferredSize(new Dimension(1200, 150));
        addAnnotationField.setForeground(Color.GRAY);

        addAnnotationField.addFocusListener(new FocusListener()
        {
            @Override
            //
            // Removes placeholder text when the user clicks on the text field
            // and replaces with new text and change color
            // Arguments:
            //      e: keyboard event
            //
            public void focusGained(FocusEvent e)
            {
                if (addAnnotationField.getText().equals(placeholderText))
                {
                    addAnnotationField.setText("");
                    addAnnotationField.setForeground(Color.BLACK);
                }
            }

            //
            // Restores placeholder text when the user clicks off the text field
            // and change color back to original
            // Arguments:
            //      e: keyboard event
            //
            @Override
            public void focusLost(FocusEvent e)
            {
                if (addAnnotationField.getText().isEmpty())
                {
                    addAnnotationField.setText(placeholderText);
                    addAnnotationField.setForeground(Color.GRAY);
                }
            }
        });

        add(addAnnotationField);

        //TO DO: when add button clicked, add to catalog
        addButton = new JButton("Add");
        add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(closeListener);
        add(cancelButton);
    }
}
