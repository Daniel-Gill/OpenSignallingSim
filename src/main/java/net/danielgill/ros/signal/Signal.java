package net.danielgill.ros.signal;

import com.almasb.fxgl.entity.Entity;

import net.danielgill.ros.block.Block;
import net.danielgill.ros.track.Direction;

public abstract class Signal {
    protected SignalAspect aspect;
    protected int x;
    protected int y;
    protected Entity entity;

    public Signal() {
        this.aspect = SignalAspect.CLEAR;
    }

    public SignalAspect getAspect() {
        return aspect;
    }

    public abstract void update(Block nextBlock);

    public abstract void draw(int x, int y, Direction direction);
}
