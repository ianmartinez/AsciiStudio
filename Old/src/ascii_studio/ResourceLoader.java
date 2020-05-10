/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ascii_studio;

import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;

/**
 *
 * @author ianma
 */
public class ResourceLoader 
{
    
    private void printEx(Exception ex) 
    {
    	System.out.println("Error:");
    	System.out.println("\tMake sure you opened a file\r\n");
    	System.out.println("Stack Trace:");
    	ex.printStackTrace();
    }
    
    public URL getResourceLocation(String name)
    {
        try 
        {
            return getClass().getClassLoader().getResource(name);
        } catch (Exception ex) {
            printEx(ex);
            return null;
        }
    }
    public Image getResourceImage(String name)
    {
        try 
        {
            return ImageIO.read(getResourceLocation(name));
        } catch (Exception ex) {
            printEx(ex);
            return null;
        }
    }
    
    public Icon getResourceIcon(String name)
    {
        try 
        {
            return new javax.swing.ImageIcon(getResourceLocation(name));
        } catch (Exception ex) {
            printEx(ex);
            return null;
        }
    }
}
