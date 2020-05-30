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

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Static class to get system fonts.
 *
 * @author Ian Martinez
 */
public final class FontUtil {

    private FontUtil() { } // No constructor

    /**
     * Get all of the available fonts on the system.
     *
     * @param onlyMonospace if only monospace fonts should be included
     *
     * @return the available fonts
     */
    public static ArrayList<String> getAllFontNames(boolean onlyMonospace) {
        var gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var fontContext = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
        var fontNames = new ArrayList<String>();
        var allFonts = gEnv.getAllFonts();

        for (Font font : allFonts) {
            var fontName = font.getFontName(Locale.US);
            if (onlyMonospace) {
                var iWidth = font.getStringBounds("i", fontContext).getWidth();
                var mWidth = font.getStringBounds("m", fontContext).getWidth();

                if (iWidth == mWidth) {
                    fontNames.add(fontName);
                }
            } else {
                fontNames.add(fontName);
            }
        }

        return fontNames;
    }
    
}
