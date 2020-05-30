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

import classserializer.*;
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

    // Common weights that are used
    public static final String STANDARD_DARK_WEIGHTS = " .'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
    public static final String STANDARD_LIGHT_WEIGHTS = reverseWeightsString(STANDARD_DARK_WEIGHTS);
    public static final String BLOCKS_DARK_WEIGHTS = "░▒▓█";
    public static final String BLOCKS_LIGHT_WEIGHTS = reverseWeightsString(BLOCKS_DARK_WEIGHTS);
    public static final String SYMBOLS_DARK_WEIGHTS = ".*^%&#@";
    public static final String SYMBOLS_LIGHT_WEIGHTS = reverseWeightsString(SYMBOLS_DARK_WEIGHTS);

    /**
     * The palette settings that all other palettes derive from.
     */
    public final static Palette basePalette = new Palette();

    private boolean usingPhrase = false;
    private boolean overridingImageColors = false;
    private Color backgroundColor = Color.BLACK;
    private Color fontColor = Color.WHITE;
    private Font font = new Font("Monospaced", Font.BOLD, 12);
    private String[] weights = STANDARD_DARK_WEIGHTS.split("");

    /**
     * Create a new palette that derives from the base palette.
     */
    public Palette() {
        if (basePalette != null) { // Not initializing the base palette
            usingPhrase = basePalette.isUsingPhrase();
            overridingImageColors = basePalette.isOverridingImageColors();
            backgroundColor = basePalette.getBackgroundColor();
            fontColor = basePalette.getFontColor();
            font = basePalette.getFont();
            weights = basePalette.getWeights();
        } else { // Init base palette
            if (Platform.isWindows()) {
                // Set a better default font on Windows
                font = new Font("Consolas", Font.BOLD, 12);
            }
        }
    }

    /**
     * Create a new palette that derives from another palette.
     *
     * @param otherPalette the palette to derive from
     */
    public Palette(Palette otherPalette) {
        usingPhrase = otherPalette.isUsingPhrase();
        overridingImageColors = otherPalette.isOverridingImageColors();
        backgroundColor = otherPalette.getBackgroundColor();
        fontColor = otherPalette.getFontColor();
        font = otherPalette.getFont();
        weights = otherPalette.getWeights();
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
     * @return the weights as a string
     */
    public String getWeightsString() {
        return String.join("", weights);
    }

    /**
     * Set the weights array from a string
     *
     * @param weightsString the weightsString to set
     */
    public void setWeightsString(String weightsString) {
        weights = weightsString.split("");
    }

    /**
     * @return the number of weights
     */
    public int getWeightCount() {
        return weights.length;
    }

    /**
     * Get the weight at a given position.
     *
     * @param pos the weight index
     *
     * @return the string for the weight at that index
     */
    public String getWeight(int pos) {
        return weights[pos];
    }

    /**
     * Get the ratio between the font height and the font width.
     *
     * @param g the graphics the font will be rendered with
     *
     * @return the font ratio
     */
    public int getFontRatio(Graphics g) {
        var metrics = g.getFontMetrics(font);
        return (int) (metrics.getHeight() / maxVal(metrics.getWidths()));
    }

    /**
     * Find the best params to get a rendered image size to match the source
     * image's size with this palette.
     *
     * @param width the source image width
     * @param height the source image height
     *
     * @return the best sampling params
     */
    public ImageSamplingParams getSamplingParams(int width, int height) {
        var testImg = new BufferedImage(25, 25, BufferedImage.TRANSLUCENT);
        var weightsSize = getStringDimensions(testImg.createGraphics(), String.join("", weights));
        var fontWidth = weightsSize.getWidth() / weights.length;
        var fontHeight = weightsSize.getHeight();

        return new ImageSamplingParams(width, height, fontWidth, fontHeight);
    }

    /**
     * Get the dimensions of a string using this palette.
     *
     * @param g the graphics the string will be rendered with
     * @param s the string to measure
     *
     * @return the string's dimensions
     */
    public Dimension getStringDimensions(Graphics g, String s) {
        var metrics = g.getFontMetrics(font);
        Rectangle2D bounds = metrics.getStringBounds(s, g);

        return new Dimension((int) bounds.getWidth(), (int) bounds.getHeight());
    }

    /**
     * Get the width of a string using this palette.
     *
     * @param g the graphics the string will be rendered with
     * @param s the string to measure
     *
     * @return the string's width
     */
    public int getStringWidth(Graphics g, String s) {
        FontMetrics metrics = g.getFontMetrics(font);
        Rectangle2D bounds = metrics.getStringBounds(s, g);

        return (int) bounds.getWidth();
    }

    /**
     * Get the height of a string using this palette.
     *
     * @param g the graphics the string will be rendered with
     * @param s the string to measure
     *
     * @return the string's height
     */
    public int getStringHeight(Graphics g, String s) {
        FontMetrics metrics = g.getFontMetrics(font);
        Rectangle2D bounds = metrics.getStringBounds(s, g);

        return (int) bounds.getHeight();
    }

    /**
     * Get the max value in an array.
     *
     * @param arr the array to search through
     *
     * @return the max value
     */
    private static int maxVal(int[] arr) {
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }

    /**
     * Import a file to create a palette. If an exception occurred while parsing
     * the file, null is returned.
     *
     * @param filePath the file representing a palette
     *
     * @return a new palette from the file
     */
    public static Palette importFile(String filePath) {
        try {
            var palette = new Palette();
            var serializer = new ClassSerializer(new FontSerializer(), new ColorSerializer());
            serializer.setIgnoreMissingValues(true);
            serializer.read(Palette.class, palette, filePath);

            return palette;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Export this palette to a file.
     *
     * @param filePath the path to export the file to
     */
    public void exportFile(String filePath) {
        var serializer = new ClassSerializer(new FontSerializer(), new ColorSerializer());
        serializer.setSkipUnknownTypes(true);
        serializer.write(Palette.class, this, filePath);
    }

    /**
     * Reverse a weights string.
     *
     * @param weightsString the weights string to reverse
     *
     * @return the reversed weights string
     */
    public static String reverseWeightsString(String weightsString) {
        var reverseStrBuilder = new StringBuilder(weightsString);
        reverseStrBuilder.reverse();

        return reverseStrBuilder.toString();
    }

}
