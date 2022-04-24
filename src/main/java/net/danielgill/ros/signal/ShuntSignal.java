package net.danielgill.ros.signal;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.block.SignalledBlock;
import net.danielgill.ros.track.Direction;

public class ShuntSignal extends Signal {

    public ShuntSignal(int xOffset, int yOffset) {
        super(xOffset, yOffset);
    }

    @Override
    public void update(Block nextBlock) {
        if(nextBlock == null) {
            this.aspect = SignalAspect.DANGER;
            drawAspect();
            return;
        }
        while(nextBlock instanceof Block && !(nextBlock instanceof SignalledBlock)) {
            if(nextBlock.getPath() != null) {
                nextBlock = nextBlock.getPath().getEndBlock();
            } else {
                this.aspect = SignalAspect.DANGER;
                drawAspect();
                return;
            }
        }
        if(nextBlock instanceof SignalledBlock) {
            this.aspect = SignalAspect.CLEAR;
            drawAspect();
            return;
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
            line = new Line(x, y - 20, x - 20, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect();
        } else if(direction == Direction.SOUTH) {
            this.x = x + 20;
            this.y = y + 20;
            line = new Line(x, y, x + 20, y);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x, y + 20, x + 20, y + 20);
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
            Arc arc = new Arc();
            arc.setCenterX(x - 2);
            arc.setCenterY(y + 6);
            arc.setRadiusX(13);
            arc.setRadiusY(13);
            arc.setStartAngle(0);
            arc.setLength(90);
            arc.setType(ArcType.ROUND);
            arc.setFill(Color.GREEN);
            entity = FXGL.entityBuilder().at(0,0).view(arc).buildAndAttach();
        } else {
            Arc arc = new Arc();
            arc.setCenterX(x - 2);
            arc.setCenterY(y + 6);
            arc.setRadiusX(13);
            arc.setRadiusY(13);
            arc.setStartAngle(0);
            arc.setLength(90);
            arc.setType(ArcType.ROUND);
            arc.setFill(Color.RED);
            entity = FXGL.entityBuilder().at(0,0).view(arc).buildAndAttach();
        }
    }
    
}
