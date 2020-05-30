/*
 * Copyright (C) 2020 ianma
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
 * Static class to check what platform the program is running on.
 *
 * @author ianma
 */
public final class Platform {

    private Platform() {} // No constructor

    /**
     * @return if the application is running on a Mac
     */
    public static boolean isMac() {
        return System.getProperty("os.name", "").startsWith("Mac OS");
    }

    /**
     * @return if the application is running on Windows
     */
    public static boolean isWindows() {
        return System.getProperty("os.name", "").startsWith("Windows");
    }

}
