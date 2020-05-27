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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * From https://stackoverflow.com/questions/33055349/jaxb-xml-to-java-awt
 */
public class FontAdapter extends XmlAdapter<FontAdapter.FontValueType, Font> {

    @Override
    public Font unmarshal(FontValueType v) throws Exception {
        return new Font(v.family, v.style, v.size);
    }

    @Override
    public FontValueType marshal(Font v) throws Exception {
        return new FontValueType(v.getFamily(), v.getStyle(), v.getSize());
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class FontValueType {
        private String family;
        private int style;
        private int size;

        public FontValueType() {
        }

        public FontValueType(String family, int style, int size) {
            this.family = family;
            this.style = style;
            this.size = size;
        }
    }
}
