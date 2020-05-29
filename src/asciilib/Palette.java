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
import java.io.File;
import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Represents the palette used to render a ASCII image.
 *
 *
 * @author Ian Martinez
 */
@XmlRootElement
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
    private String[] weights = " .'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$".split("");

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
    @XmlElement(name = "overide-image-colors")
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
    @XmlElement(name = "background-color")
    @XmlJavaTypeAdapter(ColorAdapter.class)
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
    @XmlElement(name = "font-color")
    @XmlJavaTypeAdapter(ColorAdapter.class)
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
    @XmlElement(name = "font")
    @XmlJavaTypeAdapter(FontAdapter.class)
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
    @XmlElement(name = "weights")
    public String[] getWeights() {
        return weights;
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
    @XmlElement(name = "using-phrase")
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
     * @param weights the weights to set
     */
    public void setWeights(String weights) {
        this.setWeights(weights.split(""));
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

        for (int i = 1; i < arr.length; i++) 
            if (arr[i] > max) 
                max = arr[i];
        
        return max;
    }
    
    /**
     * Import an XML file to create a palette.
     * If an exception occurred while parsing the file,
     * null is returned.
     * 
     * @param filePath the XML file representing a palette
     * 
     * @return a new palette from the XML file
     */
    public static Palette importXml(String filePath) {
        try {
            File file = new File(filePath);
            JAXBContext context = JAXBContext.newInstance(Palette.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Palette palette = (Palette)unmarshaller.unmarshal(file);
            
            return palette;
        } catch (JAXBException ex) {
            return null;
        }
    }
    
    /**
     * Export this palette to an XML file.
     * 
     * @param filePath the path to export the file to
     * 
     * @throws JAXBException if there was an exception exporting the XML file
     */
    public void exportXml(String filePath) throws JAXBException {
        File file = new File(filePath);
           
        JAXBContext context = JAXBContext.newInstance(Palette.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, file);
    }
}
