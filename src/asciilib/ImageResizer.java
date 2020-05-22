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

import java.awt.AlphaComposite;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Static class to resize images.
 *
 * @author Ian Martinez
 */
public final class ImageResizer {
    private ImageResizer() {} // No constructor
    
    public static BufferedImage resize(BufferedImage source, int width, int height) {
        var resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        var g = resizedImage.createGraphics();
        
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        
        return resizedImage;
    }
    
    public static BufferedImage getSample(BufferedImage source, double ratio) {
        var sampleWidth = (int)Math.ceil(source.getWidth() / ratio);
        var sampleHeight = (int)Math.ceil(source.getHeight() / ratio);
        
        return resize(source, sampleWidth, sampleHeight);
    }
}
