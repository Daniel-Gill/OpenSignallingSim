package net.danielgill.ros.block;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import net.danielgill.ros.path.Path;
import net.danielgill.ros.signal.Signal;
import net.danielgill.ros.signal.SignalAspect;
import net.danielgill.ros.ui.Direction;

public class SignalBlock extends Block implements SignalledBlock {
    protected Signal signal;

    public SignalBlock(String id, int x, int y, Direction direction, Signal signal) {
        super(id, x, y, direction);
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
            if(b instanceof SignalledBlock) {
                ((SignalledBlock) b).updateSignal();
            }
        }
    }

    @Override
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
        this.updateSignal();
    }

    @Override
    public void setPath(Path path) {
        if(path == null) {
            System.err.println("No path to be set for " + this.id);
            return;
        }
        if(!forwardBlocks.contains(path.getEndBlock())) {
            System.err.println(path.getStartBlock().id + " is not a forward block of " + this.id);
            return;
        }
        if(this.path != null) {
            System.err.println("Path already set for " + this.id);
            return;
        }
        if(!path.canActivate()) {
            System.err.println("Path cannot be activated for " + this.id);
            return;
        }
        
        this.path = path;
        this.nextBlock = path.getEndBlock();
        this.path.activate();
        this.updateSignal();
    }

    @Override
    public void clearPath() {
        if(this.path == null) {
            return;
        }
        this.path.deactivate();
        this.path = null;
        this.nextBlock = null;
        this.updateSignal();
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
}
