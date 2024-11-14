// ComparisonDisplay.java
//
// Popup window to display and compare two source files side-by-side
//
// Created by Lalida Krairit, 2 November 2024
//

package GUI.src.Interfaces;
import javax.swing.*;
import java.awt.*;

public class ComparisonDisplay extends JFrame
{

    //Arguments:
    //      - comparisonResult: String with the comparison result or difference
    //      - mode: String representing the comparison mode ("diskDisk" or "catCat")
    public ComparisonDisplay(String comparisonResult, String mode)
    {
        super("File Comparison");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //center

        JTextArea resultDisplay = new JTextArea();
        resultDisplay.setText(formatComparisonResult(comparisonResult, mode));
        resultDisplay.setEditable(false);

        add(new JScrollPane(resultDisplay), BorderLayout.CENTER);

        add(new JScrollPane(resultDisplay), BorderLayout.CENTER);
    }

    private String formatComparisonResult(String comparisonResult, String mode)
    {
        if ("diskDisk".equals(mode))
        {
            return "File Metadata Comparison Result:\n\n" + comparisonResult;
        } else if ("catCat".equals(mode))
        {
            return "File Content Comparison Result:\n\n" + comparisonResult;
        } else
        {
            return "Unknown comparison mode.";
        }
    }
}
