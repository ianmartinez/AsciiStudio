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
 * Static class for dealing with files.
 *
 * @author Ian Martinez
 */
public final class FileUtil {

    private FileUtil() { } // No constructor

    /**
     * Get the extension of a file, with a default extension to fallback on if
     * there is none.
     *
     * @param path the file's path
     * @param defaultExt the extension to use if there isn't one already
     *
     * @return the file's extension
     */
    public static String getExt(String path, String defaultExt) {
        int dot = path.lastIndexOf(".");
        return (dot == -1) ? defaultExt : path.substring(dot + 1).toLowerCase();
    }

    /**
     * Get the extension of a file.
     *
     * @param path the file's path
     *
     * @return the file's extension
     */
    public static String getExt(String path) {
        int dot = path.lastIndexOf(".");
        return (dot == -1) ? "" : path.substring(dot + 1).toLowerCase();
    }

    /**
     * Remove the extension from a file.
     *
     * @param path the file's path
     *
     * @return the file's path, without the extension
     */
    public static String removeExt(String path) {
        if (path.equals("")) {
            return "";
        }

        int dot = path.lastIndexOf(".");
        return (dot == -1) ? path : path.substring(0, dot);
    }

}
