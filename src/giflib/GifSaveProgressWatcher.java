/**
 * GifSaveProgressWatcher.java:
 * Interface for watching the progress of saving a GIF.
 *
 * ---
 * Written by: Ian Martinez
 * ---
 *
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA. *
 */
package giflib;

/**
 * Watch the progress of a GIF saving, frame by frame.
 *
 * @author Ian Martinez
 */
public interface GifSaveProgressWatcher {

    /**
     * Function to call to watch the progress of a GIF save.
     *
     * @param frame the frame the writer finished writing
     * @param frameCount the total number of frames to write
     */
    public void update(int frame, int frameCount);
    
}
