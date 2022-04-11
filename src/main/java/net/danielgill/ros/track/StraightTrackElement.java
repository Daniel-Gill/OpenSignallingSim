package net.danielgill.ros.track;

public class StraightTrackElement extends TrackElement {
    public StraightTrackElement(int x, int y) {
        super(x, y);
    }

    public String getImageString() {
        return "straight.png";
    }
}
