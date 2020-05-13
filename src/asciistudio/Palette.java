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

import java.awt.*;

/**
 * Represents the palette used to render a ASCII image.
 *
 *
 * @author Ian Martinez
 */
public class Palette {
    private boolean usingPhrase = false;
    private boolean overridingImageColors = false;
    private Color backgroundColor = Color.BLACK;
    private Color foregroundColor = Color.WHITE;
    private Font font = new Font("Consolas", Font.BOLD, 12);
    private String[] weights = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ".split("");
    
    public Palette() {
        
    }
    
    /**
     * Set the weights array from a string
     * @param weights the weights to set
     */
    public void setWeights(String weights) {
        this.weights = weights.split("");
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
     * @return the foregroundColor
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }

    /**
     * @param foregroundColor the foregroundColor to set
     */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
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
}
