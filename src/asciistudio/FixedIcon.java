/*
 * Copyright (C) 2025 Ian Martinez
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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Fix Java's stupid DPI icon rendering.
 */
public class FixedIcon extends ImageIcon {

    private int fixedIconSize = 32;

    public FixedIcon(URL url, int fixedIconSize) {
        super(url);
        this.fixedIconSize = fixedIconSize;

        BufferedImage i = new BufferedImage(this.fixedIconSize, this.fixedIconSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) i.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.drawImage(getImage(), 0, 0, this.fixedIconSize, this.fixedIconSize, null);
        setImage(i);
    }

    @Override
    public int getIconHeight() {
        return this.fixedIconSize;
    }

    @Override
    public int getIconWidth() {
        return this.fixedIconSize;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawImage(getImage(), x, y, c);
    }

}
