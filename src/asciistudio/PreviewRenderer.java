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
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

/**
 * Handle the rendering of the preview image in the background
 * and updating the main window when it is done.
 *
 * @author Ian Martinez
 */
public class PreviewRenderer extends SwingWorker<Void, Integer> {
    private final AsciiRenderer renderer; // The renderer
    private final BufferedImage sourceImage; // The image to use for rendering
    private final MainWindow mainWindow; // The main window to update
    private final int max; // The max progress value
    
    private BufferedImage previewImage;

    public PreviewRenderer(AsciiRenderer renderer, BufferedImage sourceImage,
            MainWindow mainWindow) {
        this.renderer = renderer;
        this.sourceImage = sourceImage;
        this.mainWindow = mainWindow;

        max = (int) renderer.getSamplingParams().getSampleHeight();
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

        previewImage = renderer.renderImage(sourceImage);

        renderer.setProgressWatcher(null);

        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            mainWindow.progressPanel.setProgress(100);
            mainWindow.renderWidthLabel.setText(previewImage.getWidth() + " px");
            mainWindow.renderHeightLabel.setText(previewImage.getHeight() + " px");
            mainWindow.renderedImageView.setIcon(new StretchIcon(previewImage));
            useRenderUI(false);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
