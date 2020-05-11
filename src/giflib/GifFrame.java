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

public class GifFrame {

    private final int delay;
    private final BufferedImage image;
    /**
     * The disposal method refers to how a new frame 
     * replaces an old frame:
     * - Unspecified: Replaces entire non-transparent frame with next frame
     * - Do not dispose: Pixels not replaced by next frame continue to display
     * - Restore to background: The background color shows through the 
     *   transparent pixels of next frame
     * - Restore to previous: Restore the area overwritten by the graphic with
     *                        what was there prior to rendering the graphic
     */
    private final String disposal;
    private final int width, height;

    public GifFrame(BufferedImage image, int delay, String disposal, int width, int height) {
        this.image = image;
        this.delay = delay;
        this.disposal = disposal;
        this.width = width;
        this.height = height;
    }

    public GifFrame(BufferedImage image) {
        this.image = image;
        this.delay = -1;
        this.disposal = null;
        this.width = -1;
        this.height = -1;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getDelay() {
        return delay;
    }

    public String getDisposal() {
        return disposal;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
