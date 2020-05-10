/**
 * Gif.java:
 * Handles the loading and saving of GIF files.
 *
 * ---
 * 
 * Created by Elliot Kroo, April 25, 2009
 * Modified by Ian Martinez, May 2020
 *
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 *
 * ---
 *
 * April 2009 (Elliot Kroo):
 *  - Initial Release
 *
 * May 2020 (Ian Martinez):
 *  - Update with newer Java constructs
 *  - Add JavaDocs
 *  - Simplified function and variable names
 *  - Customizable comments section
 *  - Per-frame delay setting
 *  - Let IO errors be handled by user of library, not the library itself
 *  - 
 */
package giflib;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.stream.*;

public final class Gif {

    private BufferedImage[] frames;
    private int delay = 0;

    public Gif(BufferedImage[] f, int d) {
        frames = f;
        delay = d;
    }

    public Gif(String filename) {
        open(filename);
    }

    public void open(String filename) {
        frames = GifSequenceWriter.getFrameImages(filename);
        delay = GifSequenceWriter.getAverageDelay(filename);
    }

    public void save(String filename) throws IOException {
        try (var output = new FileImageOutputStream(new File(filename))) {
            var writer = new GifSequenceWriter(output, frames[0].getType(), getDelay(), true);

            for (BufferedImage bi : frames) {
                writer.writeToSequence(bi);
            }

            writer.close();
        }
    }

    public int getFrameCount() {
        return frames.length;
    }

    public BufferedImage getFrame(int pos) {
        return frames[pos];
    }

    public void setFrame(int pos, BufferedImage img) {
        frames[pos] = img;
    }

    public int getDelay() {
        return delay;
    }
}
