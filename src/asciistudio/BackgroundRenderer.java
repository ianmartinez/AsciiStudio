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
import asciilib.FileUtil;
import giflib.Gif;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * Handle the rendering of ASCII art in the background and updating the main
 * window when it is done.
 *
 * @author Ian Martinez
 */
public class BackgroundRenderer extends SwingWorker<Void, RenderProgress> {

    private final AsciiRenderer renderer; // The renderer
    private final RenderType renderType; // The type of rendering to do
    private final MainWindow mainWindow; // The main window to update

    private BufferedImage sourceImage; // The image to use for rendering, if renderType != GIF
    private Gif sourceGif; // The GIF to use for rendering, if renderType == GIF
    private String outputFile; // The output file, if applicable
    private int renderMax; // The renderMax progress value

    private BufferedImage renderedImage; // The rendered image, if rendering a PREVIEW or STILL_IMAGE
    private String renderedText; // The rendered text, if rendering TEXT
    private Gif renderedGif; // The rendered GIF, if rendering GIF
    private boolean openOutputWhenComplete = true; // If the output should be opened after it is saved

    public BackgroundRenderer(AsciiRenderer renderer,
            RenderType renderType,
            MainWindow mainWindow) {
        this.renderer = renderer;
        this.renderType = renderType;
        this.mainWindow = mainWindow;
    }

    /**
     * @return the renderMax progress value
     */
    private int getMax() {
        var rows = renderer.getSamplingParams().getSampleHeight();

        if (renderType == RenderType.GIF) {
            return rows * getSourceGif().getFrameCount();
        } else {
            return rows;
        }
    }

    /**
     * If the UI should be changed because a render
     * is in progress.
     * 
     * @param renderUI the value
     */
    public void useRenderUI(boolean renderUI) {
        if (renderUI) {
            mainWindow.progressPanel.setStage("Rendering");
            mainWindow.progressPanel.setProgress(0);

            mainWindow.enableImport(false);
            mainWindow.enableEditing(false);
        } else {
            mainWindow.enableImport(true);
            mainWindow.enableEditing(true);
        }
    }

    @Override
    protected void process(List<RenderProgress> chunks) {
        RenderProgress progress = chunks.get(chunks.size() - 1);
        mainWindow.progressPanel.setStage(progress.getStage());
        mainWindow.progressPanel.setProgress(progress.getProgress(), 0, progress.getMax());
    }

    @Override
    protected Void doInBackground() throws Exception {
        renderer.setProgressWatcher((int progress, int rowCount, int frame) -> {
            var relativeProgress = (frame * rowCount) + progress;
            publish(new RenderProgress("Rendering", relativeProgress, renderMax));
        });

        // Render image as ASCII art
        switch (renderType) {
            case PREVIEW:
            case STILL_IMAGE:
                renderedImage = renderer.renderImage(sourceImage);
                break;
            case TEXT:
                renderedText = renderer.renderText(sourceImage);
                break;
            case GIF:
                renderedGif = renderer.renderGif(sourceGif);
        }

        // Save if not a preview
        if (renderType != RenderType.PREVIEW) {
            saveOutputFile();
        }

        renderer.setProgressWatcher(null);
        publish(new RenderProgress("Finished", 100, 100));

        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            mainWindow.progressPanel.setProgress(100);

            if (renderType == RenderType.PREVIEW) {
                mainWindow.renderWidthLabel.setText(renderedImage.getWidth() + " px");
                mainWindow.renderHeightLabel.setText(renderedImage.getHeight() + " px");
                mainWindow.renderedImageView.setIcon(new StretchIcon(renderedImage));
            }

        } catch (ExecutionException | InterruptedException ex) {
            var renderName = "";
            switch (renderType) {
                case PREVIEW:
                    renderName = "preview";
                    break;
                case STILL_IMAGE:
                    renderName = "image";
                    break;
                case TEXT:
                    renderName = "text";
                    break;
                case GIF:
                    renderName = "GIF";
            }

            JOptionPane.showMessageDialog(mainWindow, "Error rendering " + renderName + " (" + ex.getMessage() + ")");
        }

        useRenderUI(false);
    }

    /**
     * Open a process.
     *
     * @param process the process to open
     */
    private void openProcess(String process) {
        try {
            Desktop.getDesktop().open(new File(process));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainWindow, "Error opening '" + process + "'");
        }
    }

    /**
     * Save the rendered image to the output file.
     */
    private void saveOutputFile() {
        try {
            if (null != renderType) {
                switch (renderType) {
                    case TEXT:
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                        writer.write(renderedText);
                    }
                    break;
                    case STILL_IMAGE:
                        ImageIO.write(renderedImage, FileUtil.getExt(outputFile, "png"), new File(outputFile));
                        break;
                    case GIF:
                        renderedGif.setSaveProgressWatcher((int frame, int totalFrames) -> {
                            var relativeProgress = (int) ((frame / (double) totalFrames) * 100);
                            publish(new RenderProgress("Saving frames", relativeProgress, 100));
                        });
                        renderedGif.save(outputFile);
                        renderedGif.setSaveProgressWatcher(null);
                        break;
                    default:
                        break;
                }
            }

            if (openOutputWhenComplete) {
                openProcess(outputFile);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainWindow, "Error saving '" + outputFile + "'");
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
        this.renderMax = getMax();
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
        this.renderMax = getMax();
    }

    /**
     * @return the openOutputWhenComplete
     */
    public boolean shouldOpenOutputWhenComplete() {
        return openOutputWhenComplete;
    }

    /**
     * @param openOutputWhenComplete the openOutputWhenComplete to set
     */
    public void setOpenOutputWhenComplete(boolean openOutputWhenComplete) {
        this.openOutputWhenComplete = openOutputWhenComplete;
    }
    
}
