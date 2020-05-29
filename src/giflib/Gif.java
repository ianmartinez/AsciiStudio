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
 *  - GifSequenceWriter implements AutoCloseable so it can be used with try-with-resources
 *  - Customizable program name in comments
 *  - Let IO errors be handled by user of library, not the library itself
 *  - Use GifFrame for storing frames instead of BufferedImage array
 *  - Change delay of GIF
 *  - Add GifSaveProgressWatcher to watch the progress of a GIF save frame-by-frame (to update a progress bar)
 */
package giflib;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.stream.*;
import java.awt.Dimension;
import static giflib.GifSequenceWriter.getFrames;
import static java.awt.image.BufferedImage.*;
import java.util.ArrayList;

/**
 * A GIF that can be modified in place and opened and saved to the disk.
 */
public final class Gif {

    private GifFrame[] frames;
    private int averageDelay = -1; // Don't calculate until needed
    private GifSaveProgressWatcher saveProgressWatcher;

    /**
     * Create a new GIF with an array of image frames and the delay between
     * them.
     *
     * @param images the image frames
     * @param delay the delay between frames
     */
    public Gif(BufferedImage[] images, int delay) {
        frames = new GifFrame[images.length];

        for (int i = 0; i < frames.length; i++) {
            frames[i] = new GifFrame(images[i], delay, "");
        }
    }

    /**
     * Create a new GIF from an existing GIF file.
     *
     * @param fileName the name of the GIF file
     */
    public Gif(String fileName) {
        open(fileName);
    }

    /**
     * Create a new GIF with the same frame count and delay as another GIF
     *
     * @param gif The GIF to base the frame and delay from
     */
    public Gif(Gif gif) {
        this(new BufferedImage[gif.getFrameCount()], gif.getDelay());

        // Init empty frames
        var maxSize = maxSize(gif.getImages());
        for (GifFrame frame : frames) {
            frame.setImage(new BufferedImage(maxSize.width, maxSize.height, TYPE_INT_ARGB));
        }
    }

    /**
     * Get the maximum size of an array of images.
     *
     * @param images the images
     *
     * @return the maximum size
     */
    private Dimension maxSize(BufferedImage[] images) {
        int maxW = 0;
        int maxH = 0;

        for (var image : images) {
            maxW = Math.max(maxW, image.getWidth());
            maxH = Math.max(maxH, image.getHeight());
        }

        return new Dimension(maxW, maxH);
    }

    /**
     * Set the frames of this GIF to the frames of an existing GIF file.
     *
     * @param fileName the name of the file to open
     */
    public void open(String fileName) {
        averageDelay = -1;
        frames = getFrames(fileName);
    }

    /**
     * Save a GIF to a file.
     *
     * @param fileName the name of the file to save
     *
     * @throws IOException if there was an error saving the file
     */
    public void save(String fileName) throws IOException {
        try (var output = new FileImageOutputStream(new File(fileName));
                var writer = new GifSequenceWriter(output, frames[0].getImageType(), getDelay(), true)) {

            for (int i = 0; i < frames.length; i++) {
                var frame = frames[i];
                writer.writeToSequence(frame);

                if (saveProgressWatcher != null) {
                    saveProgressWatcher.update(i, frames.length);
                }
            }
        }
    }

    /**
     * Get the number of frames.
     *
     * @return the number of frames
     */
    public int getFrameCount() {
        return frames.length;
    }

    /**
     * Get the frame at an index
     *
     * @param frameIndex the frame's index
     *
     * @return the frame
     */
    public GifFrame getFrame(int frameIndex) {
        return frames[frameIndex];
    }

    /**
     * Set the frame at an index.
     *
     * @param pos the index of the frame to set
     * @param frame the frame
     */
    public void setFrame(int pos, GifFrame frame) {
        frames[pos] = frame;
    }

    /**
     * Get the image of a frame at an index.
     *
     * @param frameIndex the index of the frame
     *
     * @return the image of the frame
     */
    public BufferedImage getFrameImage(int frameIndex) {
        return getFrame(frameIndex).getImage();
    }

    /**
     * Set the image of a frame at an index.
     *
     * @param frameIndex the index of the frame to set
     * @param img the image
     */
    public void setFrameImage(int frameIndex, BufferedImage img) {
        frames[frameIndex].setImage(img);
    }

    /**
     * Get the delay of a frame.
     *
     * @param frameIndex the index of the frame
     *
     * @return the frame's delay
     */
    public int getFrameDelay(int frameIndex) {
        return frames[frameIndex].getDelay();
    }

    /**
     * Set the delay of a specific frame.
     *
     * @param frameIndex the index of the frame
     * @param delay the new delay
     */
    public void setFrameDelay(int frameIndex, int delay) {
        frames[frameIndex].setDelay(delay);
    }

    /**
     * Set every frame to have the same delay.
     *
     * @param delay the delay to set
     */
    public void setAllFramesDelay(int delay) {
        for (var frame : frames) {
            frame.setDelay(delay);
        }
    }

    /**
     * Get the average delay between frames.
     *
     * @return the average frame delay
     */
    public int getDelay() {
        if (averageDelay == -1) { // Not calculated
            int total = 0;

            for (GifFrame frame : frames) {
                total += frame.getDelay();
            }

            averageDelay = total / frames.length;
        }

        return averageDelay;
    }

    /**
     * Get all of the frame images.
     *
     * @return the array of frame images
     */
    public BufferedImage[] getImages() {
        var images = new ArrayList<BufferedImage>();

        for (var frame : frames) {
            images.add(frame.getImage());
        }

        return images.toArray(new BufferedImage[images.size()]);
    }

    /**
     * @return the saveProgressWatcher
     */
    public GifSaveProgressWatcher getSaveProgressWatcher() {
        return saveProgressWatcher;
    }

    /**
     * @param saveProgressWatcher the saveProgressWatcher to set
     */
    public void setSaveProgressWatcher(GifSaveProgressWatcher saveProgressWatcher) {
        this.saveProgressWatcher = saveProgressWatcher;
    }

}
