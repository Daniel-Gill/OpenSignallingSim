package net.danielgill.ros.signal;

import net.danielgill.ros.block.Block;
import net.danielgill.ros.track.Direction;

public abstract class Signal {
    protected SignalAspect aspect;

    public Signal() {
        this.aspect = SignalAspect.CLEAR;
    }

    public SignalAspect getAspect() {
        return aspect;
    }

    public abstract void update(Block nextBlock);

    public abstract void draw(int x, int y, Direction direction);
}
