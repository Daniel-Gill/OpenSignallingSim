package net.danielgill.ros.track;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.shape.Line;

public class TrackElement extends Element {
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public TrackElement(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw() {
        Line line = new Line(x1, y1, x2, y2);
        line.setStrokeWidth(7);
        FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
    }
}
