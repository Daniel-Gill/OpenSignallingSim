package net.danielgill.ros.track;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import net.danielgill.ros.path.Pathable;
import net.danielgill.ros.ui.Drawable;

public class Track implements Drawable, Pathable {
    private Entity entity;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Track(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        entity = null;
    }

    @Override
    public void draw() {
        Line line = new Line(x1, y1, x2, y2);
        line.setStrokeWidth(7);
        line.setStrokeLineCap(StrokeLineCap.BUTT);
        entity = FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
    }

    @Override
    public void update() {
    }

    @Override
    public void activate() {
        FXGL.getGameWorld().removeEntities(entity);
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.RED);
        line.setStrokeWidth(7);
        line.setStrokeLineCap(StrokeLineCap.BUTT);
        entity = FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
    }

    @Override
    public void deactivate() {
        FXGL.getGameWorld().removeEntities(entity);
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(7);
        line.setStrokeLineCap(StrokeLineCap.BUTT);
        entity = FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
    }
    
}
