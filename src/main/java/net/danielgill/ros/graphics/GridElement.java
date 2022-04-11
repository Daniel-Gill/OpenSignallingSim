package net.danielgill.ros.graphics;

import net.danielgill.ros.track.TrackElement;

public class GridElement<T extends TrackElement> {
    public int X_LOC;
    public int Y_LOC;

    public GridElement(int x, int y) {
        this.X_LOC = x;
        this.Y_LOC = y;
    }
}
