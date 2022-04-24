package net.danielgill.ros.block;

import net.danielgill.ros.path.Path;
import net.danielgill.ros.signal.Signal;
import net.danielgill.ros.track.Direction;

public class SignalledBlock extends Block {
    protected Signal signal;

    public SignalledBlock(String id, Signal signal) {
        super(id);
        this.signal = signal;
        this.signal.update(null);
    }

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

    public void drawSignal(int x, int y, Direction direction) {
        signal.draw(x, y, direction);
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
    public void addFowardBlock(Block block) {
        forwardBlocks.add(block);
        block.addBackBlock(this);
        this.updateSignal();
    }

    @Override
    public void removeForwardBlock(Block block) {
        forwardBlocks.remove(block);
        block.removeBackBlock(this);
        this.updateSignal();
    }

    public Signal getSignal() {
        return signal;
    }
}
