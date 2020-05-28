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
 * Stores data about the render process.
 *
 * @author Ian Martinez
 */
public class RenderProgress {
    private final int frameCount;
    private final int rowCount;
    private int currentFrame;
    private int currentRow;
    
    public RenderProgress(int frameCount, int rowCount) {
        this.frameCount = frameCount;
        this.rowCount = rowCount;
    }

    /**
     * @return the frameCount
     */
    public int getFrameCount() {
        return frameCount;
    }

    /**
     * @return the rowCount
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @return the currentFrame
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * @param currentFrame the currentFrame to set
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * @return the currentRow
     */
    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * @param currentRow the currentRow to set
     */
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
}