package net.danielgill.ros.signal;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.track.Direction;

public class TwoAspectSignal extends Signal {
    private double x;
    private double y;
    private Entity entity;

    public TwoAspectSignal() {
        
    }

    @Override
    public void update(Block nextBlock) {
        System.out.println("Updating signal");
        if(nextBlock == null) {
            this.aspect = SignalAspect.DANGER;
            return;
        }
        if(nextBlock.isOccupied()) {
            this.aspect = SignalAspect.DANGER;
        } else {
            this.aspect = SignalAspect.CLEAR;
        }
        drawAspect();
    }

    @Override
    public void draw(int x, int y, Direction direction) {
        Line line;
        if(direction == Direction.EAST) {
            this.x = x + 20;
            this.y = y - 20;
            line = new Line(x, y, x, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x, y - 20, x + 20, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        } else if(direction == Direction.WEST) {
            this.x = x - 20;
            this.y = y + 20;
            line = new Line(x, y, x, y + 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x, y + 20, x - 20, y + 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        } else if(direction == Direction.NORTH) {
            this.x = x - 20;
            this.y = y - 20;
            line = new Line(x, y, x - 20, y);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x - 20, y, x - 20, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        } else if(direction == Direction.SOUTH) {
            this.x = x + 20;
            this.y = y + 20;
            line = new Line(x, y, x + 20, y);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x + 20, y, x + 20, y + 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        }
    }

    private void drawAspect() {
        if(entity != null) {
            FXGL.getGameWorld().removeEntity(entity);
        }
        if(aspect == SignalAspect.CLEAR) {
            Circle c = new Circle(x, y, 6);
            c.setFill(Color.GREEN);
            entity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
        } else {
            Circle c = new Circle(x, y, 6);
            c.setFill(Color.RED);
            entity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
        }
    }
}
