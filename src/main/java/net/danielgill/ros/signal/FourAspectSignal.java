package net.danielgill.ros.signal;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.block.SignalledBlock;
import net.danielgill.ros.track.Direction;

public class FourAspectSignal extends Signal {
    private Entity secondEntity;
    private Direction dir;

    public FourAspectSignal(int xOffset, int yOffset) {
        super(xOffset, yOffset);
    }

    @Override
    public void update(Block nextBlock) {
        if(nextBlock == null) {
            this.aspect = SignalAspect.DANGER;
            drawAspect();
            return;
        }
        if(nextBlock.isOccupied()) {
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
            if(((SignalledBlock) nextBlock).getSignal().getAspect() == SignalAspect.DANGER) {
                this.aspect = SignalAspect.CAUTION;
                drawAspect();
                return;
            }
            if(((SignalledBlock) nextBlock).getSignal().getAspect() == SignalAspect.CAUTION) {
                this.aspect = SignalAspect.PRELIM_CAUTION;
                drawAspect();
                return;
            }
        }
        this.aspect = SignalAspect.CLEAR;
        drawAspect();
    }

    @Override
    public void draw(int x, int y, Direction direction) {
        Line line;
        this.dir = direction;
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

    private void drawAspect() {
        if(entity != null) {
            FXGL.getGameWorld().removeEntity(entity);
        }
        if(secondEntity != null) {
            FXGL.getGameWorld().removeEntity(secondEntity);
        }
        if(aspect == SignalAspect.CLEAR) {
            Circle c = new Circle(x, y, 6);
            c.setFill(Color.GREEN);
            entity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
        } else if(aspect == SignalAspect.DANGER) {
            Circle c = new Circle(x, y, 6);
            c.setFill(Color.RED);
            entity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
        } else if(aspect == SignalAspect.CAUTION) {
            Circle c = new Circle(x, y, 6);
            c.setFill(Color.ORANGE);
            entity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
        } else if(aspect == SignalAspect.PRELIM_CAUTION) {
            Circle c = new Circle(x, y, 6);
            c.setFill(Color.ORANGE);
            entity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();

            if(dir == Direction.EAST) {
                c = new Circle(x - 10, y, 6);
                c.setFill(Color.ORANGE);
                secondEntity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
            }
        }
    }
}
