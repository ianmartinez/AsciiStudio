package ascii_studio;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.*;

/**
 *
 * @author ianma
 */
public class ImageMatrix 
{
    private Dimension size;
    private int[][] r;
    private int[][] g;
    private int[][] b;
    
    public ImageMatrix(BufferedImage source)
    {
        size = new Dimension(source.getWidth(),source.getHeight());
        r = new int[size.width][size.height];
        g = new int[size.width][size.height];
        b = new int[size.width][size.height];
        
        for (int y = 0; y < source.getHeight(); y++) 
        {
            for (int x = 0; x < source.getWidth(); x++)
            {
                Color clr = new Color(source.getRGB(x, y));
                
                r[x][y] = clr.getRed();
                g[x][y] = clr.getGreen();
                b[x][y] = clr.getBlue();
            }
        }
    }
    
    public Color GetPixel(int x, int y)
    {
        return new Color(r[x][y],g[x][y],b[x][y]);
    }
    
    public void SetPixel(int x, int y, Color c)
    {
        r[x][y] = c.getRed();
        g[x][y] = c.getGreen();
        b[x][y] = c.getBlue();
    }
    
    public void SetR(int x, int y, int val)
    {
        r[x][y] = val;
    }
    
    public void SetG(int x, int y, int val)
    {
        g[x][y] = val;
    }
    
    public void SetB(int x, int y, int val)
    {
        b[x][y] = val;
    }
    
    public void ShiftR(int ShiftX, int ShiftY)
    {
        int[][] r2 = new int[size.width][size.height];
        
        for (int y = 0; y < size.height - ShiftY; y++) 
        {
            for (int x = 0; x < size.width - ShiftX; x++)
            {
                r2[x+ShiftX][y+ShiftY] = r[x][y];
            }
        }
        
        r = r2;
    }
    
    public BufferedImage Render()
    {
        BufferedImage img = new BufferedImage(size.width,size.height,BufferedImage.TRANSLUCENT);
        ShiftR(0,5);
        
        for (int y = 0; y < img.getHeight(); y++) 
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color clr = new Color(r[x][y],g[x][y],b[x][y]);
                img.setRGB(x, y, clr.getRGB());
            }
        }
        
        return img;
    }
}
