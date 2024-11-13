// Main.java
//
// Main logic interface betweent the database and GUI components
//
// Created by Sunidhi Pruthikosit, 13/11/2024
//

import GUI.src.Interfaces.MainFrame;
import utilities.*;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.attribute.FileTime;

public class Main
{
    public static void main(String[] args)
    {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}

