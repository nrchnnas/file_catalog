// ViewMoreDisplay.java
//
// Panel for displaying more details of files in catalog table after the view more
// button is clicked. It has functions for deleting file from catalog, editing annotation
// and viewing content of file
//
// Created by Lalida Krairit, 3 November 2024
//

package GUI.src.Interfaces;
import utilities.FileRecord;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMorePanel extends JPanel
{
    //buttons for different functions
    private JButton deleteButton, viewButton, editButton, doneButton;
    private JTable infoTable; //table to display all the information
    private JTextArea annotationArea; //where annotations will be shown
    private FileRecord fileInfo; //info about file as a struct

    public ViewMorePanel(FileRecord fileInfo)
    {
        this.fileInfo = fileInfo;
        setLayout(new BorderLayout());

        //---------------------Table----------------------
        Object[][] tableData =
        {
            {"Name", fileInfo.getFileName()},
            {"Extension", fileInfo.getFileType()},
            {"Size", formatSize(fileInfo.getFileSize())},
            {"Last Edited", fileInfo.getModificationDate()},
            {"Location", fileInfo.getFilePath()}
        };

        String[] tableColumns = {"Attribute", "Value"};

        infoTable = new JTable(tableData, tableColumns)
        {
            //Make each cell uneditable using row and column
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        infoTable.setPreferredScrollableViewportSize(new Dimension(300, 100));
        infoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(infoTable);
        add(tableScrollPane, BorderLayout.WEST);

        //------------------Table Header-------------------

        JTableHeader tableHeader = infoTable.getTableHeader();
        tableHeader.setBackground(Color.decode("#3E3E3E"));
        tableHeader.setForeground(Color.white);
        tableHeader.setReorderingAllowed(false);

        //------------------Annotation---------------------

        annotationArea = new JTextArea(fileInfo.getAnnotation());
        annotationArea.setLineWrap(true);
        annotationArea.setWrapStyleWord(true);
        annotationArea.setEditable(false);
        annotationArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane annotationScrollPane = new JScrollPane(annotationArea);
        annotationScrollPane.setBorder(BorderFactory.createTitledBorder("Annotation"));
        annotationScrollPane.setPreferredSize(new Dimension(400, 100));
        add(annotationScrollPane, BorderLayout.CENTER);

        //--------------------Buttons-----------------------

        deleteButton = new JButton("Delete from Catalog");
        viewButton = new JButton("View Contents");
        editButton = new JButton("Edit Annotation");
        doneButton = new JButton("Done");

        deleteButton.addActionListener(new DeleteAction());
        viewButton.addActionListener(new ViewAction());
        editButton.addActionListener(new EditAction());
        doneButton.addActionListener(new DoneAction());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 3));
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(doneButton);

        add(buttonPanel,BorderLayout.EAST);
    }

    //Deletes file from catalog table
    private class DeleteAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this file from the catalog?");
            if (confirm == JOptionPane.YES_OPTION)
            {
                //TO DO: Logic to delete file from catalog
            }
        }
    }

    //View content of file
    private class ViewAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            ContentDisplay contentDisplay = new ContentDisplay(fileInfo.getFileName());
            contentDisplay.setVisible(true);
        }
    }

    //Edit annotation of file
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            annotationArea.setEditable(true);
            annotationArea.setBackground(Color.WHITE);
        }
    }

    //Done action which will save annotation if there are any new annotations
    private class DoneAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //TO DO: logic to save annotation
            String updatedAnnotation = annotationArea.getText();
            annotationArea.setEditable(false);
            annotationArea.setBackground(Color.LIGHT_GRAY);
        }
    }

    //format the size
    private String formatSize(long size)
    {
        if (size < 1024) return size + " B";
        int exp = (int) (Math.log(size) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1); // K, M, G, T, etc.
        return String.format("%.1f %sB", size / Math.pow(1024, exp), unit);
    }
}
