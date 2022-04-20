package net.danielgill.ros.signal;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.track.Direction;

public class TwoAspectSignal extends Signal {
    public TwoAspectSignal() {
        
    }

    @Override
    public void update(Block nextBlock) {
        if(nextBlock == null) {
            this.aspect = SignalAspect.DANGER;
            return;
        }
        if(nextBlock.isOccupied()) {
            this.aspect = SignalAspect.DANGER;
        } else {
            this.aspect = SignalAspect.CLEAR;
        }
    }

    @Override
    public void draw(int x, int y, Direction direction) {
        Line line;
        if(direction == Direction.EAST) {
            line = new Line(x, y, x, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            line = new Line(x, y - 20, x + 20, y - 20);
            line.setStrokeWidth(4);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
            drawAspect(x + 20, y - 20);
        }
    }

    private void drawAspect(double x, double y) {
        if(aspect == SignalAspect.CLEAR) {
            Circle c = new Circle(x, y, 6);
            c.setFill(Color.GREEN);
            FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
        } else {
            Circle c = new Circle(x, y, 6);
            c.setFill(Color.RED);
            FXGL.entityBuilder().at(0,0).view(c).buildAndAttach();
        }
    }
}
