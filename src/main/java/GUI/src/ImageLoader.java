// ImageLoader.java
//
// Allows JAVA to load images for deployment using URL
//
// Created by Lalida Krairit, 19 November 2024
//

package GUI.src;
import javax.swing.*;
import java.net.URL;

public class ImageLoader
{
    // Loads image using the path
    // Returns image icon or null if image is not found
    //
    public static ImageIcon loadImage(String path)
    {
        URL imageUrl = ImageLoader.class.getResource(path);
        if (imageUrl != null)
        {
            return new ImageIcon(imageUrl);
        } else
        {
            System.err.println("Image not found: " + path);
            return null;
        }
    }
}
