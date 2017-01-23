// 
//  Gif.java
//  
//  Created by Elliot Kroo on 2009-04-25.
//
// This work is licensed under the Creative Commons Attribution 3.0 Unported
// License. To view a copy of this license, visit
// http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
// Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
package ascii_studio;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.stream.*;


public class Gif 
{
    private BufferedImage[] frames;
    private int delay = 0;

    public Gif(BufferedImage[] f, int d) 
    {
        frames = f;
        delay = d;
    }

    public Gif(String filename) 
    {
        open(filename);
    }

    public void open(String filename) 
    {
        try 
        {
            frames = GifSequenceWriter.getFrames(filename);
            delay = GifSequenceWriter.getDelay(filename);
        } catch (Exception ex) {ex.printStackTrace();}
    }

    public void save(String filename) 
    {
        try 
        {	
            ImageOutputStream output = new FileImageOutputStream(new File(filename));
            GifSequenceWriter writer = new GifSequenceWriter(output, frames[0].getType(), getDelay(), true);

            for(BufferedImage bi:frames)
                writer.writeToSequence(bi);

            writer.close();
            output.close();
        } catch (Exception ex){ex.printStackTrace();}
    }

    public int getFrameCount() 
    {
        return frames.length;
    }

    public BufferedImage getFrame(int pos) 
    {
        return frames[pos];
    }

    public void setFrame(int pos, BufferedImage img) 
    {
        frames[pos] = img;
    }

    public int getDelay() 
    {
        return delay;
    }
}