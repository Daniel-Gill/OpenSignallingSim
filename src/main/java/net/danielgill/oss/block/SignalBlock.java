package net.danielgill.oss.block;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.danielgill.oss.location.Location;
import net.danielgill.oss.signal.Signal;
import net.danielgill.oss.signal.SignalAspect;
import net.danielgill.oss.ui.Direction;

public class SignalBlock extends Block implements SignalledBlock {
    protected Signal signal;

    public SignalBlock(String id, int x, int y, Direction direction, Signal signal) {
        super(id, x, y, direction);
        this.signal = signal;
        this.signal.update(null);
    }

    public SignalBlock(String id, int x, int y, Direction direction, Signal signal, Location location) {
        super(id, x, y, direction, location);
        this.signal = signal;
        this.signal.update(null);
    }

    @Override
    public void updateSignal() {
        if(path != null) {
            signal.update(nextBlock);
        } else {
            signal.update(null);
        }
        for(Block b : backBlocks) {
            if(b == this || path == null) {
                break;
            }
            if(b instanceof SignalledBlock) {
                ((SignalledBlock) b).updateSignal();
            }
        }
    }

    @Override
    public void update() {
        if(entity != null) {
            FXGL.getGameWorld().removeEntity(entity);
        }
        if(this.isOccupied()) {
            Text t = new Text(this.getOccupantId());
            if(this.earlyOccupied) {
                t.setFill(Color.YELLOW);
            } else {
                t.setFill(Color.WHITE);
            }
            t.setX(x - 34);
            t.setY(y + 4);
            t.setFont(Font.font("monospace"));
            entity = FXGL.entityBuilder().view(t).buildAndAttach();
        }
        updateSignal();
    }

    @Override
    public void draw() {
        this.signal.draw(x, y, direction);

        Line line;
        if(direction == Direction.EAST) {
            line = new Line(x, y, x - 40, y);
            line.setStrokeWidth(20);
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        } else if(direction == Direction.WEST) {
            line = new Line(x, y, x + 40, y);
            line.setStrokeWidth(20);
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        } else if(direction == Direction.NORTH) {
            line = new Line(x, y, x, y + 40);
            line.setStrokeWidth(20);
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        } else if(direction == Direction.SOUTH) {
            line = new Line(x, y, x, y - 40);
            line.setStrokeWidth(20);
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        }
    }

    @Override
    public SignalAspect getAspect() {
        return this.signal.getAspect();
    }

    @Override
    public boolean canExit() {
        if(getAspect() == SignalAspect.DANGER) {
            return false;
        } else {
            return true;
        }
    }
}
