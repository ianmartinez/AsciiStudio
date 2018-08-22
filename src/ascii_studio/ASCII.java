package ascii_studio;

import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.*;

// converts individual images to ascii art
public class ASCII 
{
    // weights from black -> white
    public String[] weights = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ".split("");
    private Color backColor = Color.WHITE;
    private Color fontColor = Color.BLACK;
    private boolean overrideRTF = false;
    private boolean usingPhrase = false;
    private Font font = new Font("Consolas",Font.BOLD,12);

    // getters & setters
    public Color getBackColor() {return backColor;}
    public void setBackColor(Color clr) {backColor = clr;}
    public Color getFontColor() {return fontColor;}
    public void setFontColor(Color clr) {fontColor = clr;}
    public boolean getOverrideRTF() {return overrideRTF;}
    public void setOverrideRTF(boolean bool) {overrideRTF = bool;}
    public boolean getUsingPhrase() {return usingPhrase;}
    public void SetUsingPhrase(boolean bool) {usingPhrase = bool;}
    public Font getFont() {return font;}
    public void setFont(Font f) {font=f;}

    public static String getProgress(int value,int maxValue,int width) 
    {
        String str = "[";
        int curWidth = (int)((width*value)/maxValue);
        for (int i = 0; i<width-2; i++) 
        {
            if (i<=curWidth)
                str += "=";
            else 
                str += " ";
        }

        return str + "]";
    }	

    public static int getRatio(Graphics g, Font f) 
    {
        FontMetrics metrics = g.getFontMetrics(f);
        return (int)(metrics.getHeight()/maxVal(metrics.getWidths()));
    }

    public static Dimension measureLine(Graphics g, Font f, String s) 
    {
        FontMetrics metrics = g.getFontMetrics(f);
        Rectangle2D bounds = metrics.getStringBounds(s,g);

        return new Dimension((int)bounds.getWidth(), (int)bounds.getHeight());
    }

    public static int strW(Graphics g, Font f, String s) 
    {
        FontMetrics metrics = g.getFontMetrics(f);
        Rectangle2D bounds = metrics.getStringBounds(s,g);

        return (int)bounds.getWidth();
    }

    public static int strH(Graphics g, Font f, String s) 
    {
        FontMetrics metrics = g.getFontMetrics(f);
        Rectangle2D bounds = metrics.getStringBounds(s,g);

        return (int)bounds.getHeight();
    }

    private static int maxVal(int[] arr)
    {
        int max = arr[0];

        for(int i=1; i< arr.length; i++)
            if(arr[i] > max)
                max = arr[i];

        return max;	
    }

    public int getWeightCount() 
    {
        return weights.length;
    }	

    public int getLuminosityXY(BufferedImage img, int x, int y, int max)
    {
        int color = img.getRGB(x, y);
        return getLuminosity(new Color(color), max);
    }

    public int getLuminosity(Color c, int max)
    {
        int red   = (c.getRGB() >>> 16) & 0xFF;
        int green = (c.getRGB() >>>  8) & 0xFF;
        int blue  = (c.getRGB() >>>  0) & 0xFF;

        float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
        return (int)(max*luminance);
    }

    public String getWeight(Color c) 
    {
        int lum = getLuminosity(c,weights.length-1);
        return weights[lum];
    }

    public String getRow(BufferedImage img, int y) 
    {
        String val = "";
        int phrasePos = 0;
        for (int x = 0; x < img.getWidth(); x++)
        {
            if (usingPhrase)
            {
                if (phrasePos >= weights.length)
                    phrasePos = 0;
                    
                val += weights[phrasePos];
                phrasePos++;
            }
            else
            {
                val += getWeight(new Color(img.getRGB(x, y)));
            }
        }

        return val;	
    }

     public String getText(BufferedImage img) 
     {
        System.out.println("Beginning render");	
        System.out.println(img.getHeight() + " rows");	
        Graphics2D g = img.createGraphics();
        int ratio = getRatio(g,font);
        String ascii = "";

        for (int y = 0; y < img.getHeight(); y+=ratio) 
        {
            System.out.println("Processing row #" + (y+1) + " of " + (img.getHeight()-1));
            ascii += getRow(img,y) + "\r\n";
        }

        System.out.println("Render finished");	

        return ascii;	
    }

    public BufferedImage getFullImage(BufferedImage img) 
     {		
        Graphics2D g = img.createGraphics();
        int ratio = getRatio(g,font);

        System.out.println("Beginning render");	

        // measure dimensions
        System.out.println("Measuring lines");	
        ArrayList<Dimension> dimensions = new ArrayList<Dimension>();
        for (int y = 0; y < img.getHeight(); y+=ratio) 
        {
            System.out.println("Measuring line #" + (y+1));	
            String line = getRow(img,y);
            dimensions.add(measureLine(g,font,line));
        }
        
        
        // get width and height for image
        int height = 0;
        int maxWidth = 0;
        int dimPos = 0; // position in the dimensions array
        int charX = 0; // X position of the text
        int charY = dimensions.get(0).height - 3; // X position of the text = first row of characters height - offset

        for(Dimension d: dimensions) 
        {
            height += d.getHeight();
            maxWidth = Math.max((int)d.getWidth(),maxWidth);
        }

        BufferedImage renderImage = new BufferedImage(maxWidth,height, BufferedImage.TRANSLUCENT);
        Graphics2D renderGraphics = renderImage.createGraphics();
        // set background color
        renderGraphics.setColor(backColor);
        renderGraphics.fillRect(0,0,renderImage.getWidth(),renderImage.getHeight());
        for (int y = 0; y < img.getHeight(); y+=ratio) 
        {
            int phrasePos = 0;
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color clr = new Color(img.getRGB(x, y));
                
                // Get string associated with the pixel
                String str;
                if (usingPhrase)
                {
                    if (phrasePos >= weights.length)
                        phrasePos = 0;

                    str = weights[phrasePos];
                    phrasePos++;
                }
                else
                {
                    str = getWeight(clr);
                }

                if (overrideRTF)
                    renderGraphics.setColor(fontColor);
                else
                    renderGraphics.setColor(clr);

                renderGraphics.setFont(font);
                renderGraphics.drawString(str,charX,charY);

                charX += strW(renderGraphics,font,str);
                System.out.println(
                    getProgress(x*y, (img.getWidth()-1)*(img.getHeight()-1), 12) 
                    + " X: " + x + "/" + (img.getWidth() - 1) 
                    + " Y: " + y + "/" + (img.getHeight() - 1)
                ); 
            }
            charX = 0;
            charY += (int)dimensions.get(dimPos).getHeight();
            dimPos++;
        }


        System.out.println("Render finished");			
        return renderImage;	
    }
}