/*
 * Copyright (C) 2020 Ian Martinez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package asciistudio;

import asciilib.AsciiRenderer;
import giflib.Gif;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

/**
 * Handle the rendering of ASCII art in the background
 * and updating the main window when it is done.
 *
 * @author Ian Martinez
 */
public class BackgroundRenderer extends SwingWorker<Void, Integer> {
    private final AsciiRenderer renderer; // The renderer
    private final RenderType renderType; // The type of rendering to do
    private final MainWindow mainWindow; // The main window to update
    
    private BufferedImage sourceImage; // The image to use for rendering, if renderType != GIF
    private Gif sourceGif; // The GIF to use for rendering, if renderType == GIF
    private String outputFile; // The output file, if applicable
    private int max; // The max progress value
    
    private BufferedImage previewImage;

    public BackgroundRenderer(AsciiRenderer renderer, 
            RenderType renderType,
            MainWindow mainWindow) {
        this.renderer = renderer;
        this.renderType = renderType;
        this.mainWindow = mainWindow;
    }
    
    /**
     * @return the max progress value
     */
    private int getMax() {
        var rows = renderer.getSamplingParams().getSampleHeight();
        
        if(renderType == RenderType.GIF) {
            return rows * getSourceGif().getFrameCount();
        } else {
            return rows;
        }            
    }

    public void useRenderUI(boolean renderUI) {
        if (renderUI) {
            mainWindow.progressPanel.setProgress(0);

            mainWindow.enableImport(false);
            mainWindow.enableEditing(false);
        } else {
            mainWindow.enableImport(true);
            mainWindow.enableEditing(true);
        }
    }

    @Override
    protected void process(List<Integer> chunks) {
        int i = chunks.get(chunks.size() - 1);
        int val = chunks.get(chunks.size() - 1);
        mainWindow.progressPanel.setProgress(val, 0, max);
    }

    @Override
    protected Void doInBackground() throws Exception {
        renderer.setProgressWatcher((int progress) -> {
            publish(progress);
        });

        previewImage = renderer.renderImage(getSourceImage());

        renderer.setProgressWatcher(null);

        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            mainWindow.progressPanel.setProgress(100);
            
            if(renderType == RenderType.PREVIEW) {
                mainWindow.renderWidthLabel.setText(previewImage.getWidth() + " px");
                mainWindow.renderHeightLabel.setText(previewImage.getHeight() + " px");                
                mainWindow.renderedImageView.setIcon(new StretchIcon(previewImage));
            }
            
            useRenderUI(false);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the outputFile
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * @return the sourceImage
     */
    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    /**
     * @param sourceImage the sourceImage to set
     */
    public void setSourceImage(BufferedImage sourceImage) {
        this.sourceImage = sourceImage;
        this.max = getMax();
    }

    /**
     * @return the sourceGif
     */
    public Gif getSourceGif() {
        return sourceGif;
    }

    /**
     * @param sourceGif the sourceGif to set
     */
    public void setSourceGif(Gif sourceGif) {
        this.sourceGif = sourceGif;
        this.max = getMax();
    }
}
