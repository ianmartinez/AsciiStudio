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

    /**
     * Create a new ImageSamplingParams.
     *
     * @param originalWidth the image's original width
     * @param originalHeight the image's original height
     * @param fontWidth the font's width
     * @param fontHeight the font's height
     */
    public ImageSamplingParams(double originalWidth,
            double originalHeight,
            double fontWidth,
            double fontHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.fontWidth = fontWidth;
        this.fontHeight = fontHeight;

        var largestFontDimension = Math.ceil((fontHeight > fontWidth) ? fontHeight : fontWidth);
        this.samplingRatio = largestFontDimension;
    }

    /**
     * Get the sample image's height.
     *
     * @return the height of the sample image
     */
    public int getSampleHeight() {
        return (int) Math.ceil(originalHeight / getSamplingRatio() / getHeightRatio());
    }

    /**
     * Get the sample image's width.
     *
     * @return the width of the sample image
     */
    public int getSampleWidth() {
        return (int) Math.ceil(originalWidth / getSamplingRatio());
    }

    /**
     * Get the original image's width.
     *
     * @return the width of the original image
     */
    public double getOriginalWidth() {
        return originalWidth;
    }

    /**
     * Get the original image's height.
     *
     * @return the height of the original image
     */
    public double getOriginalHeight() {
        return originalHeight;
    }

    /**
     * Get the font's width.
     *
     * @return the width of the font
     */
    public double getFontWidth() {
        return fontWidth;
    }

    /**
     * Get the font's height.
     *
     * @return the height of the font
     */
    public double getFontHeight() {
        return fontHeight;
    }

    /**
     * Get the ratio of the font height to the font width.
     *
     * @return the font ratio
     */
    public int getHeightRatio() {
        // Windows doesn't need calculation for some reason,
        // but other platforms do
        if (Platform.isWindows()) {
            return 1;
        } else {
            return Math.max(1, (int) Math.round(fontHeight / fontWidth));
        }
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
