package net.danielgill.ros.signal;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.shape.Line;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.ui.Direction;

public abstract class Signal {
    protected SignalAspect aspect;
    protected int x;
    protected int y;
    protected Entity entity;
    protected int xOffset;
    protected int yOffset;
    protected Direction direction;

    public Signal(int xOffset, int yOffset) {
        this.aspect = SignalAspect.CLEAR;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public SignalAspect getAspect() {
        return aspect;
    }

    public abstract void update(Block nextBlock);

    public void draw(int x, int y, Direction direction) {
        this.direction = direction;
        Line line;
        if(direction == Direction.EAST) {
            this.x = x + 20 + xOffset;
            this.y = y - 20 + yOffset;
            x += xOffset;
            y += yOffset;
            line = new Line(x, y, x, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x, y - 20, x + 20, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        } else if(direction == Direction.WEST) {
            this.x = x - 20 + xOffset;
            this.y = y + 20 + yOffset;
            x += xOffset;
            y += yOffset;
            line = new Line(x, y, x, y + 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x, y + 20, x - 20, y + 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        } else if(direction == Direction.NORTH) {
            this.x = x - 20 + xOffset;
            this.y = y - 20 + yOffset;
            x += xOffset;
            y += yOffset;
            line = new Line(x, y, x - 20, y);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x - 20, y, x - 20, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        } else if(direction == Direction.SOUTH) {
            this.x = x + 20 + xOffset;
            this.y = y + 20 + yOffset;
            x += xOffset;
            y += yOffset;
            line = new Line(x, y, x + 20, y);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x + 20, y, x + 20, y + 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        }
    }

    protected abstract void drawAspect();
}
