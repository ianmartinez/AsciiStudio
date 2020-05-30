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
package classserializer;

import java.awt.Color;

/**
 * Implements a serializer for java.awt.Color.
 *
 * @author Ian Martinez
 */
public class ColorSerializer implements TypeSerializer<Color> {

    @Override
    public boolean matches(Class<?> type) {
        return type == Color.class;
    }

    @Override
    public String serialize(Color value) {
        return String.format("%d,%d,%d,%d", 
                value.getRed(), 
                value.getGreen(), 
                value.getBlue(), 
                value.getAlpha());
    }

    @Override
    public Color parse(String value) {
        var colorParts = value.split(",");
        var r = Integer.parseInt(colorParts[0]);
        var g = Integer.parseInt(colorParts[1]);
        var b = Integer.parseInt(colorParts[2]);
        var a = Integer.parseInt(colorParts[3]);
        
        return new Color(r, g, b, a);
    }
    
}
