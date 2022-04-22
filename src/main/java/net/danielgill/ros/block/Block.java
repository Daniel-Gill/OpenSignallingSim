package net.danielgill.ros.block;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.ros.path.Path;
import net.danielgill.ros.signal.Signal;

public class Block {
    private String id;
    private List<Block> forwardBlocks;
    private List<Block> backBlocks;

    // trains in block
    private boolean occupied;
    //private Train train;

    // path from block
    private Path path;
    private Block nextBlock;

    // signal at front of block
    private Signal signal;

    public Block(String id, Signal signal) {
        this.id = id;
        this.signal = signal;
        this.signal.update(null);
        forwardBlocks = new ArrayList<>();
        backBlocks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
        this.updateSignal();
    }

    public void updateSignal() {
        if(path != null) {
            signal.update(nextBlock);
        } else {
            signal.update(null);
        }
        for(Block b : backBlocks) {
            b.updateSignal();
        }
    }

    public void setPath(Path path) {
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

    public void clearPath() {
        if(this.path == null) {
            return;
        }
        this.path.deactivate();
        this.path = null;
        this.nextBlock = null;
        this.updateSignal();
    }

    public void addFowardBlock(Block block) {
        forwardBlocks.add(block);
        block.addBackBlock(this);
        this.updateSignal();
    }

    public void removeForwardBlock(Block block) {
        forwardBlocks.remove(block);
        block.removeBackBlock(this);
        this.updateSignal();
    }

    private void addBackBlock(Block block) {
        backBlocks.add(block);
    }

    private void removeBackBlock(Block block) {
        backBlocks.remove(block);
    }

    public Signal getSignal() {
        return signal;
    }
}
