package net.danielgill.ros.path;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.ros.block.Block;
import net.danielgill.ros.track.TrackElement;

public class Path {
    private String id;
    private Block startBlock;
    private Block endBlock;
    private List<TrackElement> trackElements;
    private boolean active;
    private List<Path> interlocks;

    public Path(String id, Block startBlock, Block endBlock) {
        this.id = id;
        this.startBlock = startBlock;
        this.endBlock = endBlock;
        trackElements = new ArrayList<>();
        interlocks = new ArrayList<>();
    }

    public Path(Block startBlock, Block endBlock) {
        this.id = startBlock.getId() + "-" + endBlock.getId();
        this.startBlock = startBlock;
        this.endBlock = endBlock;
        trackElements = new ArrayList<>();
        interlocks = new ArrayList<>();
    }

    public void addTrackElement(TrackElement trackElement) {
        trackElements.add(trackElement);
    }

    public void addInterlock(Path interlock) {
        interlocks.add(interlock);
    }

    public String getId() {
        return id;
    }

    public Block getStartBlock() {
        return startBlock;
    }

    public Block getEndBlock() {
        return endBlock;
    }

    public boolean isActive() {
        return active;
    }

    public boolean canActivate() {
        for(Path interlock : interlocks) {
            if(interlock.isActive()) {
                return false;
            }
        }
        return true;
    }

    public void activate() {
        if(canActivate()) {
            active = true;
            for(TrackElement t : trackElements) {
                t.activate();
            }
        }
    }

    public void deactivate() {
        active = false;
        for(TrackElement t : trackElements) {
            t.deactivate();
        }
    }
}
