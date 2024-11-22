// Main.java
//
// A Java application for managing a catalog of source files.
// The application provides various functionalities related to source file management.
// Main file for the application will serve as entry point for running the program.
//
// Created by Lalida Krairit, 19 October 2024
//

import GUI.src.MainFrame;
import utilities.FileCatalog;

public class MainGUI {
    public static void main(String[] args) {
        // Initialize database table before any database interaction
        FileCatalog.initializeCatalog();

        // Start your application logic
        System.out.println("Starting application...");

        // Launch the main GUI frame
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}


