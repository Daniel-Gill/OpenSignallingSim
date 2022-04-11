package net.danielgill.ros.track;

public abstract class TrackElement {
    public int X_LOC;
    public int Y_LOC;

    public TrackElement(int x, int y) {
        this.X_LOC = x;
        this.Y_LOC = y;
    }

    public abstract String getImageString();
}
