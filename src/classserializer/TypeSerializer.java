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

/**
 * An interface to serialize a given type T.
 *
 * @author Ian Martinez
 * @param <T> The type to serialize
 */
public interface TypeSerializer<T> {

    /**
     * If this serializer can be used for for the a type.
     *
     * @param type the type
     *
     * @return true if this serializer can be used
     */
    boolean matches(Class<?> type);

    /**
     * Serialize the type to a string.
     *
     * @param value the value to serialize as a string
     *
     * @return the value as a string
     */
    String serialize(T value);

    /**
     * Parse the value from a string.
     *
     * @param value the value to parse
     *
     * @return the parsed value
     */
    T parse(String value);

}
