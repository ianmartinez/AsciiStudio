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
 * Watch the progress of an image render.
 *
 * @author Ian Martinez
 */
public interface RenderProgressWatcher {

    /**
     * Function to call to update the progress of a render.
     *
     * @param progress the new progress value
     * @param rowCount the total row count
     * @param frame the frame the renderer is rendering
     */
    public void update(int progress, int rowCount, int frame);

}
