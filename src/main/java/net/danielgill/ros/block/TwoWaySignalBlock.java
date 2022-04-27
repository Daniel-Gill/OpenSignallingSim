package net.danielgill.ros.block;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import net.danielgill.ros.path.Path;
import net.danielgill.ros.signal.Signal;
import net.danielgill.ros.signal.SignalAspect;
import net.danielgill.ros.ui.Direction;

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
        //print signal status
        System.out.println(this.id + ": " + mainSignal.getAspect() + " " + oppositeSignal.getAspect());
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

}
