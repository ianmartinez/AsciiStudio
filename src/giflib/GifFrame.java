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

public final class GifFrame {
    private int delay;
    private BufferedImage image;
    private int width, height;
    /**
     * The disposal method refers to how a new frame replaces an old frame: -
     * Unspecified: Replaces entire non-transparent frame with next frame - Do
     * not dispose: Pixels not replaced by next frame continue to display -
     * Restore to background: The background color shows through the transparent
     * pixels of next frame - Restore to previous: Restore the area overwritten
     * by the graphic with what was there prior to rendering the graphic
     */
    private final String disposal;

    public GifFrame(BufferedImage image, int delay, String disposal) {
        this.image = image;
        this.delay = delay;
        this.disposal = disposal;
        refreshDimensions();
    }

    public GifFrame(BufferedImage image) {
        this.image = image;
        this.delay = -1;
        this.disposal = null;
        refreshDimensions();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage img) {
        image = img;
        refreshDimensions();
    }
    
    private void refreshDimensions() {        
        width = image.getWidth();
        height = image.getHeight();
    }

    public int getDelay() {
        return delay;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getDisposal() {
        return disposal;
    }

    public int getImageType() {
        return image.getType();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
