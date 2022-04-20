package net.danielgill.ros.signal;

import net.danielgill.ros.block.Block;

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
    
}
