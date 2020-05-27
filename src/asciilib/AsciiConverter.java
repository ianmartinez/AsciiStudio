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
package asciilib;

import giflib.Gif;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * A converter to images into ASCII art.
 *
 * @author Ian Martinez
 */
public class AsciiConverter {
    private final Palette palette;
    private int phrasePos = 0;
    private int pixelPos = 0;

    /**
     * Create a new ASCII converter with a palette.
     * 
     * @param palette the palette to use when rendering
     */
    public AsciiConverter(Palette palette) {
        this.palette = palette;
    }
    
    public void updateProgress(int newProgress) {

    }

    /**
     * Get the luminosity of a pixel in an image.
     * 
     * @param img the image
     * @param x the x position of the pixel
     * @param y the y position of the pixel
     * @param max the max luminosity
     * 
     * @return the pixel's luminosity
     */
    public int getLuminosityXY(BufferedImage img, int x, int y, int max) {
        int color = img.getRGB(x, y);
        return getLuminosity(new Color(color), max);
    }

    /**
     * Get the luminosity of a color.
     * 
     * @param color the color 
     * @param max the max luminosity
     * 
     * @return the color's luminosity
     */
    public int getLuminosity(Color color, int max) {
        int red = (color.getRGB() >>> 16) & 0xFF;
        int green = (color.getRGB() >>> 8) & 0xFF;
        int blue = (color.getRGB() >>> 0) & 0xFF;

        float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
        return (int) (max * luminance);
    }

    /**
     * Get the corresponding weight to a color.
     * 
     * @param color the color
     * 
     * @return the weight for the color
     */
    public String getWeight(Color color) {
        int lum = getLuminosity(color, palette.getWeightCount() - 1);
        return palette.getWeight(lum);
    }

    /**
     * Render a row of text from a row of pixels in an image.
     * 
     * @param img the image
     * @param y the y position of the row of pixels
     * 
     * @return the rendered text
     */
    public String renderTextRow(BufferedImage img, int y) {
        String val = "";
        for (int x = 0; x < img.getWidth(); x++) {
            if (palette.isUsingPhrase()) {
                if (getPhrasePos() >= palette.getWeightCount()) {
                    setPhrasePos(0);
                }

                val += palette.getWeight(getPhrasePos());
                setPhrasePos(getPhrasePos() + 1);
            } else {
                val += getWeight(new Color(img.getRGB(x, y)));
            }
        }

        return val;
    }

    /**
     * Render ASCII art text derived from an image.
     * 
     * @param img the image to derive the pixel data from
     * 
     * @return the ASCII art text
     */
    public String renderText(BufferedImage img) {
        Graphics2D g = img.createGraphics();
        int ratio = palette.getFontRatio(g);
        String ascii = "";

        for (int y = 0; y < img.getHeight(); y += ratio) {
            ascii += renderTextRow(img, y) + "\r\n";
        }

        return ascii;
    }

    /**
     * Render an ASCII art image derived from another image.
     * 
     * @param img the image to derive the pixel data from
     * 
     * @return the rendered ASCII art image
     */
    public BufferedImage renderImage(BufferedImage img) {
        var sourceGraphics = img.createGraphics();
        int ratio = palette.getFontRatio(sourceGraphics);

        // Measure dimensions line by line
        var dimensions = new ArrayList<Dimension>();
        for (int y = 0; y < img.getHeight(); y += ratio) {
            String line = renderTextRow(img, y);
            dimensions.add(palette.getStringDimensions(sourceGraphics, line));
        }

        // Get width and height for image
        int height = 0;
        int maxWidth = 0;
        int dimPos = 0; // Position in the dimensions array
        int charX = 0; // X position of the text
        int charY = dimensions.get(0).height - 3; // X position of the text = first row of characters height - offset

        for (Dimension d : dimensions) {
            height += d.getHeight();
            maxWidth = Math.max((int) d.getWidth(), maxWidth);
        }

        var renderImage = new BufferedImage(maxWidth, height, BufferedImage.TRANSLUCENT);
        var renderGraphics = renderImage.createGraphics();
        // Set background color
        renderGraphics.setColor(palette.getBackgroundColor());
        renderGraphics.fillRect(0, 0, renderImage.getWidth(), renderImage.getHeight());
        for (int y = 0; y < img.getHeight(); y += ratio) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color pixelColor = new Color(img.getRGB(x, y));

                // Get string associated with the pixel
                String str;
                if (palette.isUsingPhrase()) {
                    if (getPhrasePos() >= palette.getWeightCount()) {
                        setPhrasePos(0);
                    }

                    str = palette.getWeight(getPhrasePos());
                    setPhrasePos(getPhrasePos() + 1);
                } else {
                    str = getWeight(pixelColor);
                }

                if (palette.isOverridingImageColors()) {
                    renderGraphics.setColor(palette.getFontColor());
                } else {
                    renderGraphics.setColor(pixelColor);
                }

                renderGraphics.setFont(palette.getFont());
                renderGraphics.drawString(str, charX, charY);

                charX += palette.getStringWidth(renderGraphics, str);
                updateProgress(++pixelPos);
            }
            
            charX = 0;
            charY += (int) dimensions.get(dimPos).getHeight();
            dimPos++;
        }

        return renderImage;
    }

    /**
     * Render a still image and save it to a file.
     * 
     * @param filePath the file to save to
     * @param img the source image
     * 
     * @throws IOException if there was an error writing the file
     */
    public void saveImage(String filePath, BufferedImage img) throws IOException {
        File outFile = new File(filePath);
        BufferedImage render = renderImage(img);
        ImageIO.write(render, "gif", outFile);
    }

    /**
     * Render a GIF and save it to a file.
     * 
     * @param filePath the file to save to
     * @param gif the source GIF
     * 
     * @throws IOException if there was an error writing the file
     */
    public void saveGif(String filePath, Gif gif) throws IOException {
        var convertedGif = new Gif(gif);
        
        for (var i = 0; i < gif.getFrameCount(); i++) {
            var convertedFrame = renderImage(gif.getFrameImage(i));
            gif.setFrameImage(i, convertedFrame);
        }
        
        convertedGif.save(filePath);
    }

    /**
     * @return the phrasePos
     */
    public int getPhrasePos() {
        return phrasePos;
    }

    /**
     * @param phrasePos the phrasePos to set
     */
    public void setPhrasePos(int phrasePos) {
        this.phrasePos = phrasePos;
    }
}