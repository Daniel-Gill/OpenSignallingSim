package net.danielgill.ros.block;

import net.danielgill.ros.signal.Signal;
import net.danielgill.ros.track.Direction;

public class TwoWaySignalledBlock extends SignalledBlock {
    private Signal oppositeSignal;

    public TwoWaySignalledBlock(String id, Signal signal, Signal oppositeSignal) {
        super(id, signal);
        this.oppositeSignal = oppositeSignal;
        this.oppositeSignal.update(null);
    }

    @Override
    public void updateSignal() {
        if(path != null) {
            if(path.getDirection() == this.direction) {
                signal.update(nextBlock);
            } else {
                oppositeSignal.update(nextBlock);
            }
        } else {
            signal.update(null);
            oppositeSignal.update(null);
        }
        for(Block b : backBlocks) {
            if(b instanceof SignalledBlock) {
                ((SignalledBlock) b).updateSignal();
            }
        }
    }

    @Override
    public void drawSignal(int x, int y, Direction direction) {
        signal.draw(x, y, direction);
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
    
}
