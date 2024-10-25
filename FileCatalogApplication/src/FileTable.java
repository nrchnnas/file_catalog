// FileTable.java
//
// This will create the table to display all the files in the disk
//
// Created by Lalida Krairit, 25 October 2024
//

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;


public class FileTable
{
    private JTable table;
    private JScrollPane scrollPane;

    public FileTable()
    {

        //TO DO: make rows dynamic based on the number of files in the directory
        //TO DO: be able to convert strings to integer and date format

        String[] columns = {"Name", "Ext.", "Size", "Last Edited Date"};
        Object[][] data =
                {
                {"Program", "C", "176B", "21.08.2019 17:00"},
                {"Document", "txt", "5KB", "10.11.2020 09:00"},
                {"Image", "png", "1.2MB", "05.06.2021 15:30"},
                {"Music", "mp3", "3MB", "12.12.2022 12:00"}
        };

        table = new JTable(data, columns);
        table.setRowHeight(20);
        table.setBackground(Color.white);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.decode("#3E3E3E"));
        tableHeader.setForeground(Color.white);

        table.setFillsViewportHeight(true); // Ensures the table occupies full height in the viewport
        //table.setBackground(Color.decode("#CFCFCF"));

        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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

}
