// ComparisonDisplay.java
//
// Popup window to display and compare two source files side-by-side
// or to show metadata differences if comparing disk files
//
// Created by Lalida Krairit, 2 November 2024
//

package GUI.src.Interfaces;
import javax.swing.*;
import java.awt.*;

public class ComparisonDisplay extends JFrame {

    /**
     * Constructs the ComparisonDisplay to show file content or metadata differences.
     *
     * Arguments:
     *      - comparisonResult: String with the comparison result or difference
     *      - mode: String representing the comparison mode ("diskDisk" or "catCat")
     */
    public ComparisonDisplay(String comparisonResult, String mode) {
        super("File Comparison");

        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JTextArea resultDisplay = new JTextArea();
        resultDisplay.setText(formatComparisonResult(comparisonResult, mode));
        resultDisplay.setEditable(false);

        // Wrap the text area in a scroll pane for easy viewing
        add(new JScrollPane(resultDisplay), BorderLayout.CENTER);
    }

    /**
     * Formats the comparison result text based on the mode (diskDisk or catCat).
     *
     * Arguments:
     *      - comparisonResult: the raw result string from the comparison
     *      - mode: the comparison mode, either "diskDisk" (metadata) or "catCat" (content)
     *
     * Returns:
     *      - A formatted string with context for display
     */
    private String formatComparisonResult(String comparisonResult, String mode) {
        if ("diskDisk".equals(mode)) {
            return "File Metadata Comparison Result:\n\n" + comparisonResult;
        } else if ("catCat".equals(mode)) {
            return "File Content Comparison Result:\n\n" + comparisonResult;
        } else {
            return "Unknown comparison mode.";
        }
    }
}
