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

import java.awt.Font;

/**
 * Implements a serializer for java.awt.Font. 
 *
 * @author Ian Martinez
 */
public class FontSerializer implements TypeSerializer<Font> {

    @Override
    public boolean matches(Class<?> type) {
        return type == Font.class;
    }

    @Override
    public String serialize(Font value) {
        return String.format("%s,%d,%d",
                value.getFamily(),
                value.getStyle(),
                value.getSize());
    }

    @Override
    public Font parse(String value) {
        var fontParts = value.split(",");
        var family = fontParts[0];
        var style = Integer.parseInt(fontParts[1]);
        var size = Integer.parseInt(fontParts[2]);
        
        return new Font(family, style, size);
    }
    
}
