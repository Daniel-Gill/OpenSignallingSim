package net.danielgill.ros.block;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.ros.signal.Signal;

public class Block {
    private String id;
    private List<Block> forwardBlocks;
    private List<Block> backBlocks;

    // trains in block
    private boolean occupied;
    //private Train train;

    // paths from block
    private boolean automatic; // automatic will set the path to next block
    private Block automaticBlock;
    private boolean pathed;
    private Block pathBlock;

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
        if(automatic) {
            signal.update(automaticBlock);
        } else if(pathed) {
            signal.update(pathBlock);
        } else {
            signal.update(null);
        }
        for(Block b : backBlocks) {
            b.updateSignal();
        }
    }

    public void setAutomatic(Block automaticBlock) {
        if(!forwardBlocks.contains(automaticBlock)) {
            throw new IllegalArgumentException("Block " + automaticBlock.getId() + " is not a forward block of " + this.getId());
        }
        if(this.automatic || this.pathed) {
            throw new IllegalStateException("Block " + this.getId() + " already has a path");
        }
        automatic = true;
        this.automaticBlock = automaticBlock;
        pathed = false;
        pathBlock = null;
        this.updateSignal();
    }

    public void setPath(Block pathBlock) {
        if(!forwardBlocks.contains(pathBlock)) {
            throw new IllegalArgumentException("Block " + pathBlock.getId() + " is not a forward block of " + this.getId());
        }
        if(this.pathed || this.automatic) {
            throw new IllegalStateException("Block " + this.getId() + " already has a path");
        }
        automatic = false;
        this.pathBlock = pathBlock;
        pathed = true;
        automaticBlock = null;
        this.updateSignal();
    }

    public void clearPath() {
        automatic = false;
        pathed = false;
        automaticBlock = null;
        pathBlock = null;
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
