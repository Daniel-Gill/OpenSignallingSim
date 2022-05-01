package net.danielgill.oss.signal;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.block.SignalledBlock;

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

    protected void drawAspect() {
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
