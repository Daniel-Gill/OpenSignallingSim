package net.danielgill.ros.signal;

import net.danielgill.ros.block.Block;
import net.danielgill.ros.track.Direction;

public class FourAspectSignal extends Signal {
    public FourAspectSignal() {
        
    }

    @Override
    public void update(Block nextBlock) {
        if(nextBlock == null) {
            this.aspect = SignalAspect.DANGER;
            return;
        }
        if(nextBlock.isOccupied()) {
            this.aspect = SignalAspect.DANGER;
            return;
        }
        if(nextBlock.getSignal().getAspect() == SignalAspect.DANGER) {
            this.aspect = SignalAspect.CAUTION;
            return;
        }
        if(nextBlock.getSignal().getAspect() == SignalAspect.CAUTION) {
            this.aspect = SignalAspect.PRELIM_CAUTION;
            return;
        }
        this.aspect = SignalAspect.CLEAR;
    }

    @Override
    public void draw(int x, int y, Direction direction) {
        // TODO Auto-generated method stub
        
    }
}
