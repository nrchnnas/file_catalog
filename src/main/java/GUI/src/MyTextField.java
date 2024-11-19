// MyTextField.java
//
// This class creates a custom text field with a placeholder text that disappears when the user clicks on the text field
//
// Created by Lalida Krairit, 24 October 2024
//

package GUI.src;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MyTextField extends JTextField implements FocusListener
{

    private final String placeholder; //placeholder text inside the text field

    MyTextField(String placeholder)
    {
        this.placeholder = placeholder;
        this.setText(placeholder);
        this.setForeground(Color.GRAY);
        this.setBackground(Color.WHITE);
        this.addFocusListener(this);
    }

    //
    // Removes placeholder text when the user clicks on the text field
    // Arguments:
    //      e: keyboard event
    //
    @Override
    public void focusGained(FocusEvent e)
    {
        if (this.getText().equals(placeholder))
        {
            this.setText("");
            this.setForeground(Color.BLACK);
        }
    }

    // Restores placeholder text when the user clicks off the text field
    // Arguments:
    //      e: keyboard event
    //
    @Override
    public void focusLost(FocusEvent e)
    {
        if (this.getText().isEmpty())
        {
            this.setText(placeholder);
            this.setForeground(Color.GRAY);
        }
    }

    //----------------Public Function---------------

    //Gets input text inside text field
    //Returns empty string if text is same as placeholder, otherwise the string
    public String getInputText()
    {
        String text = super.getText();
        return text.equals(placeholder) ? "" : text;
    }

    // Add focus-clearing behavior to a parent component
    public void addExternalClickListener(Container parent)
    {
        parent.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                // Check if the click is outside the text field
                if (!MyTextField.this.getBounds().contains(e.getPoint()))
                {
                    MyTextField.this.transferFocus();
                }
            }
        });
    }
}
