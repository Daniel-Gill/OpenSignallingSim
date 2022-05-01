package net.danielgill.oss.path;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.oss.block.Block;
import net.danielgill.oss.track.Track;
import net.danielgill.oss.ui.Direction;

public class Path {
    private String id;
    private Block startBlock;
    private Block endBlock;
    private List<Track> track;
    private boolean active;
    private List<Path> interlocks;
    private Direction direction;

    public Path(Block startBlock, Block endBlock, Direction direction) {
        this.id = startBlock.getId() + "-" + endBlock.getId();
        this.startBlock = startBlock;
        this.endBlock = endBlock;
        track = new ArrayList<>();
        interlocks = new ArrayList<>();
        this.direction = direction;
    }

    public void addTrack(Track Track) {
        track.add(Track);
    }

    public void addInterlock(Path interlock) {
        interlocks.add(interlock);
        interlock.addInterlockNR(this);
    }

    private void addInterlockNR(Path interlock) {
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
            for(Track t : track) {
                t.activate();
            }
        }
    }

    public void deactivate() {
        active = false;
        for(Track t : track) {
            t.deactivate();
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int getTotalDistance() {
        int distance = 0;
        for(Track t : track) {
            distance += t.getDistance();
        }
        return distance;
    }
}
