package net.danielgill.ros.signal;

import net.danielgill.ros.block.Block;

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
}
