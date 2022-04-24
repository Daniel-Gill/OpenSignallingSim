package net.danielgill.ros.block;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.ros.path.Path;
import net.danielgill.ros.track.Direction;

public class Block {
    protected String id;
    protected List<Block> forwardBlocks;
    protected List<Block> backBlocks;
    protected Direction direction;

    // trains in block
    protected boolean occupied;
    //private Train train;

    // path from block
    protected Path path;
    protected Block nextBlock;

    public Block(String id) {
        this.id = id;
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
    }

    public void clearPath() {
        if(this.path == null) {
            return;
        }
        this.path.deactivate();
        this.path = null;
        this.nextBlock = null;
    }

    public void addFowardBlock(Block block) {
        forwardBlocks.add(block);
        block.addBackBlock(this);
    }

    public void removeForwardBlock(Block block) {
        forwardBlocks.remove(block);
        block.removeBackBlock(this);
    }

    public Path getPath() {
        return path;
    }

    protected void addBackBlock(Block block) {
        backBlocks.add(block);
    }

    protected void removeBackBlock(Block block) {
        backBlocks.remove(block);
    }

    public String getOccupantId() {
        //TODO replace with Train
        return "1A01";
    }

}
