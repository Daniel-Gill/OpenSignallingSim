package net.danielgill.ros.signal;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.block.SignalledBlock;
import net.danielgill.ros.ui.Direction;

public class FourAspectSignal extends Signal {
    private Entity secondEntity;

    public FourAspectSignal(int xOffset, int yOffset) {
        super(xOffset, yOffset);
    }

    @Override
    public void update(Block nextBlock) {
        if(nextBlock == null || nextBlock.isOccupied()) {
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
        SignalledBlock b = (SignalledBlock) nextBlock;
        if(b.getAspect() == SignalAspect.DANGER) {
            this.aspect = SignalAspect.CAUTION;
            drawAspect();
            return;
        } else if(b.getAspect() == SignalAspect.CAUTION) {
            this.aspect = SignalAspect.PRELIM_CAUTION;
            drawAspect();
            return;
        } else if(b.getAspect() == SignalAspect.PRELIM_CAUTION) {
            this.aspect = SignalAspect.CLEAR;
            drawAspect();
            return;
        } else if(b.getAspect() == SignalAspect.CLEAR) {
            this.aspect = SignalAspect.CLEAR;
            drawAspect();
            return;
        }

        this.aspect = SignalAspect.CLEAR;
        drawAspect();
    }

    @Override
    protected void drawAspect() {
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

            if(direction == Direction.EAST) {
                c = new Circle(x - 10, y, 6);
                c.setFill(Color.ORANGE);
                secondEntity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
            } else if(direction == Direction.WEST) {
                c = new Circle(x + 10, y, 6);
                c.setFill(Color.ORANGE);
                secondEntity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
            } else if(direction == Direction.NORTH) {
                c = new Circle(x, y - 10, 6);
                c.setFill(Color.ORANGE);
                secondEntity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
            } else if(direction == Direction.SOUTH) {
                c = new Circle(x, y + 10, 6);
                c.setFill(Color.ORANGE);
                secondEntity = FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
            }
        }
    }
}
