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

public class TwoWaySignalBlock extends Block implements SignalledBlock {
    private Signal mainSignal;
    private Signal oppositeSignal;

    public TwoWaySignalBlock(String id, int x, int y, Direction direction, Signal signal, Signal oppositeSignal) {
        super(id, x, y, direction);
        this.mainSignal = signal;
        this.oppositeSignal = oppositeSignal;
        this.mainSignal.update(null);
        this.oppositeSignal.update(null);
    }

    public TwoWaySignalBlock(String id, int x, int y, Direction direction, Location location, Signal signal, Signal oppositeSignal) {
        super(id, x, y, direction, location);
        this.mainSignal = signal;
        this.oppositeSignal = oppositeSignal;
        this.mainSignal.update(null);
        this.oppositeSignal.update(null);
    }

    @Override
    public void updateSignal() {
        if(path != null) {
            if(path.getDirection() == this.direction) {
                mainSignal.update(nextBlock);
                oppositeSignal.update(null);
            } else {
                oppositeSignal.update(nextBlock);
                mainSignal.update(null);
            }
        } else {
            mainSignal.update(null);
            oppositeSignal.update(null);
        }
        for(Block b : backBlocks) {
            if(b == this || path == null) {
                break;
            }
            b.update();
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

    public void drawSignals(int x, int y, Direction direction) {
        mainSignal.draw(x, y, direction);
        if(direction == Direction.EAST) {
            oppositeSignal.draw(x - 40, y, direction.getOpposite());
        } else if(direction == Direction.WEST) {
            oppositeSignal.draw(x + 40, y, direction.getOpposite());
        } else if(direction == Direction.NORTH) {
            oppositeSignal.draw(x, y + 40, direction.getOpposite());
        } else if(direction == Direction.SOUTH) {
            oppositeSignal.draw(x, y - 40, direction.getOpposite());
        }
    }
    
    @Override
    public void draw() {
        this.drawSignals(x, y, direction);

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
        if(this.path == null) {
            return SignalAspect.DANGER;
        }
        if(this.path.getDirection() == this.direction) {
            return mainSignal.getAspect();
        } else {
            return oppositeSignal.getAspect();
        }
    }

    @Override
    public boolean canExit() {
        if(this.getAspect() == SignalAspect.DANGER) {
            return false;
        } else {
            return true;
        }
    }

}
