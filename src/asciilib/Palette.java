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

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Represents the palette used to render a ASCII image.
 *
 *
 * @author Ian Martinez
 */
public class Palette {

    /**
     * The palette settings that all other palettes derive from.
     */
    public final static Palette basePalette = new Palette();

    private boolean usingPhrase = false;
    private boolean overridingImageColors = false;
    private Color backgroundColor = Color.BLACK;
    private Color fontColor = Color.WHITE;
    private Font font = new Font("Monospaced", Font.BOLD, 12);
    private String[] weights = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ".split("");

    public Palette() {
        if (basePalette != null) { // Not initializing the base palette
            usingPhrase = basePalette.isUsingPhrase();
            overridingImageColors = basePalette.isOverridingImageColors();
            backgroundColor = basePalette.getBackgroundColor();
            fontColor = basePalette.getFontColor();
            font = basePalette.getFont();
            weights = basePalette.getWeights();
        }
    }

    public Palette(Palette otherPalette) {
        usingPhrase = otherPalette.isUsingPhrase();
        overridingImageColors = otherPalette.isOverridingImageColors();
        backgroundColor = otherPalette.getBackgroundColor();
        fontColor = otherPalette.getFontColor();
        font = otherPalette.getFont();
        weights = otherPalette.getWeights();
    }

    /**
     * Set the weights array from a string
     *
     * @param weights the weights to set
     */
    public void setWeights(String weights) {
        this.setWeights(weights.split(""));
    }

    /**
     * @return the overridingImageColors
     */
    public boolean isOverridingImageColors() {
        return overridingImageColors;
    }

    /**
     * @param overrideImageColors the overridingImageColors to set
     */
    public void setOverridingImageColors(boolean overrideImageColors) {
        this.overridingImageColors = overrideImageColors;
    }

    /**
     * @return the backgroundColor
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the fontColor
     */
    public Color getFontColor() {
        return fontColor;
    }

    /**
     * @param fontColor the fontColor to set
     */
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the weights
     */
    public String[] getWeights() {
        return weights;
    }

    /**
     * @return the weights as a string
     */
    public String getWeightsString() {
        return String.join("", weights);
    }

    /**
     * @param weights the weights to set
     */
    public void setWeights(String[] weights) {
        this.weights = weights;
    }

    /**
     * @return the usingPhrase
     */
    public boolean isUsingPhrase() {
        return usingPhrase;
    }

    /**
     * @param usingPhrase the usingPhrase to set
     */
    public void setUsingPhrase(boolean usingPhrase) {
        this.usingPhrase = usingPhrase;
    }

    /**
     * @return the number of weights
     */
    public int getWeightCount() {
        return weights.length;
    }

    /**
     * @param pos the weight index
     * @return the string for the weight at that index
     */
    public String getWeight(int pos) {
        return weights[pos];
    }

    public int getFontRatio(Graphics g) {
        var metrics = g.getFontMetrics(font);
        return (int) (metrics.getHeight() / maxVal(metrics.getWidths()));
    }

    public Dimension measureLine(Graphics g, String s) {
        var metrics = g.getFontMetrics(font);
        Rectangle2D bounds = metrics.getStringBounds(s, g);

        return new Dimension((int) bounds.getWidth(), (int) bounds.getHeight());
    }

    /**
     * Find the best ratio to get a rendered image size to match 
     * the source image's size with this palette.
     * 
     * @param width the source image width
     * @param height the source image height
     * 
     * @return the best sampling ratio.
     */
    public double getSamplingRatio(int width, int height) {
        var testImg = new BufferedImage(25, 25, BufferedImage.TRANSLUCENT);
        var weightsSize = measureLine(testImg.createGraphics(), String.join("", weights));
        var fontWidth = weightsSize.getWidth() / weights.length;
        var fontHeight = weightsSize.getHeight();
        var samplingWidth = width / fontWidth;
        var samplingHeight = height / fontHeight;
        
        if (fontHeight > fontWidth) {
            return Math.ceil(height / samplingHeight);
        } else {
            return Math.ceil(width / samplingWidth);
        }
    }

    public int getStringWidth(Graphics g, String s) {
        FontMetrics metrics = g.getFontMetrics(font);
        Rectangle2D bounds = metrics.getStringBounds(s, g);

        return (int) bounds.getWidth();
    }

    public int getStringHeight(Graphics g, String s) {
        FontMetrics metrics = g.getFontMetrics(font);
        Rectangle2D bounds = metrics.getStringBounds(s, g);

        return (int) bounds.getHeight();
    }

    private static int maxVal(int[] arr) {
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }
}
