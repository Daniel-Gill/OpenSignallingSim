package net.danielgill.oss.signal;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.block.SignalBlock;

public class TwoAspectSignal extends Signal {
    public TwoAspectSignal(int xOffset, int yOffset) {
        super(xOffset, yOffset); 
    }

    @Override
    public void update(Block nextBlock) {
        if(nextBlock == null) {
            this.aspect = SignalAspect.DANGER;
            drawAspect();
            return;
        }
        while(nextBlock instanceof Block && !(nextBlock instanceof SignalBlock)) {
            if(nextBlock.getPath() != null) {
                nextBlock = nextBlock.getPath().getEndBlock();
            } else {
                this.aspect = SignalAspect.DANGER;
                drawAspect();
                return;
            }
        }
        if(nextBlock instanceof SignalBlock) {
            this.aspect = SignalAspect.CLEAR;
            drawAspect();
            return;
        }
        drawAspect();
    }

    @Override
    protected void drawAspect() {
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
