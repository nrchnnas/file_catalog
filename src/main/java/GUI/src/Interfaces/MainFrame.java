package GUI.src.Interfaces;

// MainFrame.java
//
// MyFrame sets up the frame for the application to layout all the components/buttons/labels.
//
// Created by Lalida Krairit, 23 October 2024
//

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener
{

    JButton openFileButton; //open file button
    JButton editFileNameButton; //edit file name button
    JButton compareButton; //compare source files button
    JButton validateButton; //validate catalog button
    JButton addButton; //add to catalog button
    JButton moveFileButton; //move source files button
    JPanel lowerPanel; //the lower panel of the frame
    FilePanel filePanel; //reference to the panel containing the file table
    CatalogPanel catalogPanel; //reference to the panel containing catalog table
    private JPanel currentOpenPanel;

    public MainFrame()
    {

        Color LIGHT_GRAY = Color.decode("#E8E8E8");
        Color DARK_GRAY = Color.decode("#CFCFCF");

        ImageIcon OPEN_ICON = new ImageIcon("src/main/java/assets/Open.png");
        ImageIcon EDIT_ICON = new ImageIcon("src/main/java/assets/Edit.png");
        ImageIcon COMPARE_ICON = new ImageIcon("src/main/java/assets/Compare.png");
        ImageIcon VALIDATE_ICON = new ImageIcon("src/main/java/assets/Refresh.png");
        ImageIcon ADD_ICON = new ImageIcon("src/main/java/assets/Add.png");
        ImageIcon MOVE_ICON = new ImageIcon("src/main/java/assets/Move.png");

        //-------------------------Buttons--------------------------

        openFileButton = new JButton(OPEN_ICON);
        openFileButton.addActionListener(this);
        openFileButton.setText("Open File");
        openFileButton.addActionListener(this);

        editFileNameButton = new JButton(EDIT_ICON);
        editFileNameButton.addActionListener(this);
        editFileNameButton.setText("Edit File name");
        editFileNameButton.addActionListener(this);

        addButton = new JButton(ADD_ICON);
        addButton.setFocusable(false);
        addButton.setText("Add to catalog");
        addButton.addActionListener(this);
        addButton.setEnabled(false);

        moveFileButton = new JButton(MOVE_ICON);
        moveFileButton.setFocusable(false);
        moveFileButton.setText("Move Source files");
        moveFileButton.addActionListener(this);
        moveFileButton.setEnabled(false);

        compareButton = new JButton(COMPARE_ICON);
        compareButton.setFocusable(false);
        compareButton.setText("Compare Source Files");
        compareButton.addActionListener(this);

        validateButton = new JButton(VALIDATE_ICON);
        validateButton.setFocusable(false);
        validateButton.setText("Validate Catalog");
        validateButton.addActionListener(this);

        //-----------------------Upper Panel------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        upperPanel.add(openFileButton);
        upperPanel.add(editFileNameButton);
        upperPanel.add(addButton);
        upperPanel.add(moveFileButton);
        upperPanel.add(compareButton);
        upperPanel.add(validateButton);

        //----------------------Center Panel------------------------

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        filePanel = new FilePanel();
        catalogPanel = new CatalogPanel(this);
        setupFileTableListener();

        filePanel.getFileTable().getTable().getSelectionModel().addListSelectionListener(e -> updateButtonStates());
        catalogPanel.getCatalogTable().getTable().getSelectionModel().addListSelectionListener(e -> updateButtonStates());

        //setting the layout of the two tables using Grid Bag Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35; // 35% of the width
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(filePanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65; // 65% of the width
        gbc.weighty = 1.0;
        centerPanel.add(catalogPanel, gbc);

        //-----------------------Lower Panel-------------------------

        lowerPanel = new JPanel();
        lowerPanel.setBackground(DARK_GRAY);
        lowerPanel.setPreferredSize(new Dimension(100, 175));
        lowerPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                filePanel.getFileTable().getTable().clearSelection();
                catalogPanel.getCatalogTable().getTable().clearSelection();
            }
        });

        //-----------------------Main Panel--------------------------

        this.getContentPane().setBackground(LIGHT_GRAY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        int width = (int) (screenWidth / (1440 / 1024));
        int height = (int) (screenHeight / (1440 / 1024));
        this.setSize(width, height);

        this.setLocationRelativeTo(null); //center this
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.add(upperPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(lowerPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Opens selected file
        if (e.getSource() == openFileButton)
        {
            openSelectedFile();
        // Edits selected file name
        } else if (e.getSource() == editFileNameButton)
        {
            int selectedRow = filePanel.getFileTable().getTable().getSelectedRow();
            String currentFileName = (String) filePanel.getFileTable().getTable().getValueAt(selectedRow, 0);
            String newFileName = JOptionPane.showInputDialog(this, "Enter new file name:", currentFileName);
            if (newFileName != null && !newFileName.trim().isEmpty())
            {
                filePanel.getFileTable().getTable().setValueAt(newFileName, selectedRow, 0);
            }

            //TO DO: change file name in actual directory

        // Adds the add annotation panel to lowerPanel if row is selected
        } else if (e.getSource() == addButton && filePanel.getFileTable().isRowSelected())
        {
            String pathName = filePanel.getFileTable().getSelectedFilePath(); // Get selected file path
            AddToCatalogPanel addPanel = new AddToCatalogPanel(pathName, event -> clearLowerPanel(), () -> catalogPanel.getCatalogTable().refreshTable());
            displayPanel(addPanel);
            currentOpenPanel = addPanel;

        // Adds the move file panel to lowerPanel if row is selected
        } else if (e.getSource() == moveFileButton && filePanel.getFileTable().isRowSelected())
        {
            // Wrap MoveFilePanel in a final array to allow usage within the lambda
            final MoveFilePanel[] movePanelHolder = new MoveFilePanel[1];
            //MoveFilePanel arguments: 1. closeListener/event, 2. Runnable action for selecting a new directory, 3. table reference
            movePanelHolder[0] = new MoveFilePanel(
                    //1
                    event -> clearLowerPanel(),
                    //2
                    () ->
                    {
                        int selectedRow = filePanel.getFileTable().getTable().getSelectedRow(); //get row
                        if (selectedRow != -1)
                        {
                            String newDirPath = filePanel.getFileTable().getSelectedPathName(); //get name of directory
                            movePanelHolder[0].setNewDirectory(newDirPath); // Update MoveFilePanel with the selected directory
                        }
                    },
                    //3
                    filePanel.getFileTable()
            );
            displayPanel(movePanelHolder[0]);


        // Adds the compare file panel to lowerPanel
        }  else if (e.getSource() == compareButton)
        {
            // Create a holder array for CompareFilePanel to reference within the lambda
            final CompareFilePanel[] compareFilePanelHolder = new CompareFilePanel[1];

            //CompareFilePanel arguments: 1. closeListener/event, 2. Runnable action for selecting a file, 3. table reference, 4. catalog reference
            compareFilePanelHolder[0] = new CompareFilePanel(
                    //1
                    event -> clearLowerPanel(),
                    //2
                    () -> {
                        String selectedFile = null;

                        if ("diskDisk".equals(compareFilePanelHolder[0].getComparisonMode()))
                        {
                            int selectedRow = filePanel.getFileTable().getTable().getSelectedRow();
                            if (selectedRow != -1)
                            {
                                selectedFile = filePanel.getFileTable().getSelectedPathName();
                            }
                        } else if ("catCat".equals(compareFilePanelHolder[0].getComparisonMode()))
                        {
                            int selectedRow = catalogPanel.getCatalogTable().getTable().getSelectedRow();
                            if (selectedRow != -1)
                            {
                                selectedFile = catalogPanel.getCatalogTable().getSelectedPathName();
                            }
                        }

                        // Set the selected file in the appropriate tag
                        if (selectedFile != null)
                        {
                            if (!compareFilePanelHolder[0].fileOneTag.isVisible())
                            {
                                compareFilePanelHolder[0].setFirstTag(selectedFile);
                            } else
                            {
                                compareFilePanelHolder[0].setSecondTag(selectedFile);
                            }
                        }
                    },
                    //3
                    filePanel.getFileTable(),
                    //4
                    catalogPanel.getCatalogTable()
            );
            // Display the CompareFilePanel in the lower panel
            displayPanel(compareFilePanelHolder[0]);
        }


        else if (e.getSource() == validateButton)
        {
            //TO DO: implement validate function and update the message
            JOptionPane.showMessageDialog(this, "");

        }
    }

    //Displays panels after clicking a specific button and repainting lowerPanel
    //Arguments:
    //      -panel: panel to be displayed
    public void displayPanel(JPanel panel)
    {
        lowerPanel.removeAll();
        lowerPanel.add(panel);
        lowerPanel.revalidate();
        lowerPanel.repaint();
        currentOpenPanel = panel;
    }

    //Clears components within lower panel
    public void clearLowerPanel()
    {
        lowerPanel.removeAll();
        lowerPanel.revalidate();
        lowerPanel.repaint();
        currentOpenPanel = null;
    }

    //Update button states based on row selection of each tables
    private void updateButtonStates()
    {
        boolean filePanelRowSel = filePanel.getFileTable().isRowSelected(); //boolean if row in file table is selected or not
        boolean catPanelRowSel = catalogPanel.getCatalogTable().isRowSelected();  //boolean if row in catalog table is selected or not
        boolean bothSelected = filePanelRowSel && catPanelRowSel; //boolean if both selected

        addButton.setEnabled(filePanelRowSel);
        moveFileButton.setEnabled(filePanelRowSel);

        if (bothSelected)
        {
            openFileButton.setEnabled(false);
            editFileNameButton.setEnabled(false);
        }
        else
        {
            openFileButton.setEnabled(true);
            editFileNameButton.setEnabled(true);
        }
    }


    // Add a ListSelectionListener to the FilePanel’s file table
    private void setupFileTableListener()
    {
        filePanel.getFileTable().getTable().getSelectionModel().addListSelectionListener(e ->
        {
            // Check if the currently open panel is an instance of AddToCatalogPanel
            if (currentOpenPanel instanceof AddToCatalogPanel && filePanel.getFileTable().isRowSelected())
            {
                String newPathName = filePanel.getFileTable().getSelectedFilePath(); // Get new selected file path
                ((AddToCatalogPanel) currentOpenPanel).updateFilePath(newPathName); // Update AddToCatalogPanel path
            }
        });
    }

    // Method to open the selected file in the default application
    private void openSelectedFile()
    {
        int selectedRow = filePanel.getFileTable().getTable().getSelectedRow();
        String filePath = filePanel.getFileTable().getSelectedFilePath(); // Get the file path

        if (filePath != null)
        {
            File file = new File(filePath);
            if (file.exists() && file.isFile())
            {
                try
                {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.OPEN))
                    {
                        desktop.open(file);
                    } else
                    {
                        JOptionPane.showMessageDialog(this, "Open action is not supported on this platform.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(this, "Could not open the file. Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (UnsupportedOperationException ex)
                {
                    JOptionPane.showMessageDialog(this, "Desktop API is not supported on this platform.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else
            {
                JOptionPane.showMessageDialog(this, "The selected file does not exist or is not a file.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else
        {
            JOptionPane.showMessageDialog(this, "No file selected.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}




