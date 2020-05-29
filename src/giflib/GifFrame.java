/**
 * GifFrame.java
 *
 * Created by Elliot Kroo, April 25, 2009
 * Modified by Ian Martinez, May 2020
 *
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package giflib;

import java.awt.image.BufferedImage;

/**
 * An individual frame in a GIF.
 */
public final class GifFrame {

    private int delay;
    private BufferedImage image;
    private int width, height;
    /**
     * The disposal method refers to how a new frame replaces an old frame.
     *
     * Unspecified: Replaces entire non-transparent frame with next frame Do not
     * dispose: Pixels not replaced by next frame continue to display.
     *
     * Restore to background: The background color shows through the transparent
     * pixels of next frame.
     *
     * Restore to previous: Restore the area overwritten by the graphic with
     * what was there prior to rendering the graphic.
     */
    private final String disposal;

    /**
     * Create new GIF frame.
     *
     * @param image the frame's image
     * @param delay the frame's delay
     * @param disposal the frame's disposal method
     */
    public GifFrame(BufferedImage image, int delay, String disposal) {
        this.image = image;
        this.delay = delay;
        this.disposal = disposal;
        refreshDimensions();
    }

    /**
     * Create a new GIF frame.
     *
     * @param image the frame's image
     */
    public GifFrame(BufferedImage image) {
        this.image = image;
        this.delay = -1;
        this.disposal = null;
        refreshDimensions();
    }

    /**
     * Update the width and height values.
     */
    private void refreshDimensions() {
        if (image != null) {
            width = image.getWidth();
            height = image.getHeight();
        }
    }

    /**
     * @return the frame's image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Set the frame's image.
     *
     * @param img the frame's image
     */
    public void setImage(BufferedImage img) {
        image = img;
        refreshDimensions();
    }

    /**
     * @return the frame's delay
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Set the frame delay.
     *
     * @param delay the frame's delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * @return the frame's disposal method
     */
    public String getDisposal() {
        return disposal;
    }

    /**
     * @return the type of the frame's image
     */
    public int getImageType() {
        return image.getType();
    }

    /**
     * @return the width of the frame image
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height of the frame image
     */
    public int getHeight() {
        return height;
    }

}
