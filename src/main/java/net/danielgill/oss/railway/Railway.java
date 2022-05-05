package net.danielgill.oss.railway;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.block.SignalBlock;
import net.danielgill.oss.block.TwoWaySignalBlock;
import net.danielgill.oss.path.Path;
import net.danielgill.oss.signal.FourAspectSignal;
import net.danielgill.oss.signal.ShuntSignal;
import net.danielgill.oss.track.Track;
import net.danielgill.oss.ui.Direction;
import net.danielgill.oss.ui.Drawable;
import net.danielgill.oss.ui.Selectable;

public class Railway {
    private List<Drawable> drawables;
    private List<Block> blocks;
    private List<Path> paths;

    public Railway() {
        drawables = new ArrayList<>();
        blocks = new ArrayList<>();
        paths = new ArrayList<>();
    }

    public void addBlock(Block b) {
        blocks.add(b);
        drawables.add(b);
    }

    public void addPath(Path p) {
        paths.add(p);
    }

    public void draw() {
        drawables.forEach(e -> e.draw());
        drawables.forEach(e -> e.update());
    }

    public Block getBlockByID(String id) {
        for (Block block : blocks) {
            if (block.getId().equals(id)) {
                return block;
            }
        }
        return null;
    }

    public Path getPathByID(String id) {
        for (Path path : paths) {
            if (path.getId().equals(id)) {
                return path;
            }
        }
        return null;
    }

    public Block getBlockAt(Point2D pos) {
        for(Drawable d : drawables) {
            if(d instanceof Selectable) {
                if(((Selectable) d).isSelected(pos)) {
                    return (Block) d;
                }
            }
        }
        return null;
    }
}
