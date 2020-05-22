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

/**
 * The parameters needed for an image sample.
 *
 * @author Ian Martinez
 */
public class ImageSamplingParams {
    private final double originalWidth, originalHeight, fontWidth, fontHeight;
    private double samplingRatio;
    
    public ImageSamplingParams(double originalWidth, 
                             double originalHeight,
                             double fontWidth,
                             double fontHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.fontWidth = fontWidth;
        this.fontHeight = fontHeight;
        samplingRatio = Math.ceil((fontHeight > fontWidth) ? fontHeight : fontWidth);
    }
    
    public double getSampleHeight() {
        return (int)Math.ceil(originalHeight / getSamplingRatio() / getHeightRatio());        
    }
    
    public double getSampleWidth() {
        return (int)Math.ceil(originalWidth / getSamplingRatio());
    }


    /**
     * @return the originalWidth
     */
    public double getOriginalWidth() {
        return originalWidth;
    }

    /**
     * @return the originalHeight
     */
    public double getOriginalHeight() {
        return originalHeight;
    }

    /**
     * @return the fontWidth
     */
    public double getFontWidth() {
        return fontWidth;
    }

    /**
     * @return the fontHeight
     */
    public double getFontHeight() {
        return fontHeight;
    }
    
    public int getHeightRatio() {
        return Math.max(1, (int) Math.round(fontHeight/fontWidth));
    }
    /**
     * @return the samplingRatio
     */
    public double getSamplingRatio() {
        return samplingRatio;
    }

    /**
     * @param samplingRatio the samplingRatio to set
     */
    public void setSamplingRatio(double samplingRatio) {
        this.samplingRatio = samplingRatio;
    }
}
