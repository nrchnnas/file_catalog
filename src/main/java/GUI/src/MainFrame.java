// MainFrame.java
//
// MyFrame sets up the frame for the application to layout all the components/buttons/labels.
//
// Created by Lalida Krairit, 23 October 2024
//

package GUI.src;
import utilities.FileCatalog;
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
    JButton validateButton; //validate catalog button
    JButton addButton; //add to catalog button
    JButton moveFileButton; //move source files button
    JPanel lowerPanel; //the lower panel of the frame
    FilePanel filePanel; //reference to the panel containing the file table
    CatalogPanel catalogPanel; //reference to the panel containing catalog table
    private JPanel currentOpenPanel; //tracks current open panel

    public MainFrame()
    {

        Color lightGray = Color.decode("#E8E8E8");
        Color darkGray = Color.decode("#CFCFCF");

        ImageIcon openIcon = ImageLoader.loadImage("/assets/Open.png");
        ImageIcon validateIcon = ImageLoader.loadImage("/assets/Refresh.png");
        ImageIcon addIcon = ImageLoader.loadImage("/assets/Add.png");
        ImageIcon moveIcon = ImageLoader.loadImage("/assets/Move.png");

        //-------------------------Buttons--------------------------

        openFileButton = new JButton(openIcon);
        openFileButton.addActionListener(this);
        openFileButton.setText("Open File");
        openFileButton.setEnabled(false);

        addButton = new JButton(addIcon);
        addButton.setText("Add to catalog");
        addButton.addActionListener(this);
        addButton.setEnabled(false);

        moveFileButton = new JButton(moveIcon);
        moveFileButton.setText("Move Source files");
        moveFileButton.addActionListener(this);
        moveFileButton.setEnabled(false);

        validateButton = new JButton(validateIcon);
        validateButton.setText("Validate Catalog");
        validateButton.addActionListener(this);

        //-----------------------Upper Panel------------------------

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        upperPanel.add(openFileButton);
        upperPanel.add(addButton);
        upperPanel.add(moveFileButton);
        upperPanel.add(validateButton);

        //----------------------Center Panel------------------------

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        filePanel = new FilePanel();
        catalogPanel = new CatalogPanel(this, this::clearLowerPanel);
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
        lowerPanel.setBackground(darkGray);
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

        this.getContentPane().setBackground(lightGray);

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
        if (e.getSource() == openFileButton)
        {
            openSelectedFile();
        // Adds the add annotation panel to lowerPanel if row is selected. If it is either directory, or already in the catalog, there is an error
        } else if (e.getSource() == addButton && filePanel.getFileTable().isRowSelected())
        {
            addFileToCatalog();
        // Adds the move file panel to lowerPanel if row is selected
        } else if (e.getSource() == moveFileButton && filePanel.getFileTable().isRowSelected()) {
            handleMoveFile();
        }
    }

    //----------------Private Function---------------

    //Update button states based on row selection of each tables
    //
    private void updateButtonStates()
    {
        boolean filePanelRowSel = filePanel.getFileTable().isRowSelected(); //boolean if row in file table is selected or not
        boolean catPanelRowSel = catalogPanel.getCatalogTable().isRowSelected();  //boolean if row in catalog table is selected or not
        boolean bothSel = filePanelRowSel && catPanelRowSel; //boolean when both are selected

        openFileButton.setEnabled(filePanelRowSel);
        addButton.setEnabled(filePanelRowSel);
        moveFileButton.setEnabled(filePanelRowSel);

        if (bothSel)
        {
            openFileButton.setEnabled(false);
            addButton.setEnabled(false);
            moveFileButton.setEnabled(false);
        }
    }

    // Add a ListSelectionListener to the FilePanel’s file table
    //
    private void setupFileTableListener()
    {
        filePanel.getFileTable().getTable().getSelectionModel().addListSelectionListener(e ->
        {
            // Check if the currently open panel is an instance of AddToCatalogPanel
            if (currentOpenPanel instanceof AddToCatalogPanel && filePanel.getFileTable().isRowSelected())
            {
                String newPathName = filePanel.getFileTable().getSelectedPathName(); // Get new selected file path
                ((AddToCatalogPanel) currentOpenPanel).updateFilePath(newPathName); // Update AddToCatalogPanel path
            }
        });
    }

    // Method to open the selected file in the default application and handling errors
    //
    private void openSelectedFile()
    {
        String filePath = filePanel.getFileTable().getSelectedPathName(); // Get the file path

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

    // Method to add file to catalog and handle checking of whether its a directory or duplicate
    //
    private void addFileToCatalog()
    {
        String pathName = filePanel.getFileTable().getSelectedPathName(); // Get selected file path
        File selectedFile = new File(pathName);
        if (!selectedFile.isDirectory())
        {
            if (!FileCatalog.isFileInCatalog(selectedFile.getPath()))
            {
                AddToCatalogPanel addPanel = new AddToCatalogPanel(pathName, event -> clearLowerPanel(), () -> catalogPanel.getCatalogTable().refreshTable(), this);
                displayPanel(addPanel);
                currentOpenPanel = addPanel;
            }
            else
            {
                JOptionPane.showMessageDialog(this, "This file is already in the catalog.");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Cannot add a directory to the catalog");
        }
    }

    // Method to handle file relocation
    //
    private void handleMoveFile()
    {
        // Wrap MoveFilePanel in a final array to allow usage within the lambda
        final MoveFilePanel[] movePanelHolder = new MoveFilePanel[1];
        String pathName = filePanel.getFileTable().getSelectedPathName();
        File selectedFile = new File(pathName);
        if (!selectedFile.isDirectory())
        {
            //MoveFilePanel arguments: 1. closeListener/event, 2. Runnable action for selecting a new directory,
            // 3. path name, 4. table reference, 5. mainFrame reference, 6. clear lower panel
            movePanelHolder[0] = new MoveFilePanel(
                    //1
                    event -> clearLowerPanel(),
                    //2
                    () ->
                    {
                        int selectedRow = filePanel.getFileTable().getTable().getSelectedRow(); //get row
                        if (selectedRow != -1)
                        {
                            String path = filePanel.getFileTable().getSelectedPathName(); //get name of selected path name
                            movePanelHolder[0].setNewDirectory(path);
                        }
                    },
                    //3
                    pathName,
                    //4
                    filePanel.getFileTable(),
                    //5
                    this,
                    //6
                    this::clearLowerPanel
            );
            displayPanel(movePanelHolder[0]);
        } else
        {
            JOptionPane.showMessageDialog(this, "No file selected.", "Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    //----------------Public Function---------------

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
    //
    public void clearLowerPanel()
    {
        lowerPanel.removeAll();
        lowerPanel.revalidate();
        lowerPanel.repaint();
        currentOpenPanel = null;
    }
}




