package net.danielgill.ros.railway;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.block.SignalBlock;
import net.danielgill.ros.block.TwoWaySignalBlock;
import net.danielgill.ros.path.Path;
import net.danielgill.ros.signal.FourAspectSignal;
import net.danielgill.ros.signal.ShuntSignal;
import net.danielgill.ros.track.Track;
import net.danielgill.ros.ui.Direction;
import net.danielgill.ros.ui.Drawable;
import net.danielgill.ros.ui.Selectable;

public class Railway {
    private List<Drawable> drawables;
    private List<Block> blocks;
    private List<Path> paths;

    public Railway() {
        drawables = new ArrayList<>();
        blocks = new ArrayList<>();
        paths = new ArrayList<>();
    }

    /**
     * Temp method to generate a railway.
     */
    public void build() {
        buildBlocks();
        buildPaths();
        buildInterlocks();
        buildTracks();
    }

    private void buildBlocks() {
        addBlock(new TwoWaySignalBlock("7", 200, 200, Direction.EAST, new FourAspectSignal(30, 0), new FourAspectSignal(-30, 0)));
        addBlock(new SignalBlock("2", 250, 100, Direction.EAST, new FourAspectSignal(0, 0)));
        addBlock(new SignalBlock("4", 350, 150, Direction.WEST, new FourAspectSignal(0, 0)));
        addBlock(new SignalBlock("3", 400, 100, Direction.EAST, new FourAspectSignal(0, 0)));
        addBlock(new TwoWaySignalBlock("5", 210, 150, Direction.WEST, new FourAspectSignal(0, 0), new ShuntSignal(0, 0)));
        addBlock(new SignalBlock("1", 140, 100, Direction.EAST, new FourAspectSignal(0, 0)));
        addBlock(new SignalBlock("6", 100, 150, Direction.WEST, new FourAspectSignal(0, 0)));
    }

    private void buildPaths() {
        buildPath("1", "2", Direction.EAST);
        buildPath("2", "3", Direction.EAST);
        buildPath("4", "5", Direction.WEST);
        buildPath("4", "7", Direction.WEST);

        buildPath("5", "6", Direction.WEST);
        buildPath("5", "3", Direction.EAST);

        buildPath("7", "3", Direction.EAST);
    }

    private void buildInterlocks() {
        getPathByID("4-5").addInterlock(getPathByID("5-3"));
        getPathByID("2-3").addInterlock(getPathByID("5-3"));

        getPathByID("4-5").addInterlock(getPathByID("4-7"));
        getPathByID("5-3").addInterlock(getPathByID("4-7"));

        getPathByID("4-7").addInterlock(getPathByID("7-3"));
        getPathByID("4-5").addInterlock(getPathByID("7-3"));
        getPathByID("5-3").addInterlock(getPathByID("7-3"));
        getPathByID("2-3").addInterlock(getPathByID("7-3"));
    }

    private void buildTracks() {
        List<Path> paths = new ArrayList<>();
        buildTrack(100, 200, 130, 200, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-7"));
        buildTrack(130, 200, 160, 200, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-7"));
        buildTrack(200, 200, 230, 200, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-7"));
        paths.add(getPathByID("7-3"));
        buildTrack(230, 200, 275, 200, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-7"));
        paths.add(getPathByID("7-3"));
        buildTrack(275, 200, 300, 150, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-5"));
        paths.add(getPathByID("4-7"));
        paths.add(getPathByID("7-3"));
        buildTrack(300, 150, 320, 150, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-5"));
        paths.add(getPathByID("4-7"));
        buildTrack(320, 150, 350, 150, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("5-3"));
        paths.add(getPathByID("7-3"));
        buildTrack(320, 150, 340, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("2-3"));
        paths.add(getPathByID("5-3"));
        paths.add(getPathByID("7-3"));
        buildTrack(340, 100, 360, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("2-3"));
        buildTrack(250, 100, 340, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-5"));
        paths.add(getPathByID("5-3"));
        buildTrack(320, 150, 250, 150, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("5-6"));
        buildTrack(210, 150, 140, 150, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("1-2"));
        buildTrack(210, 100, 140, 100, paths);
    }

    private void addBlock(Block b) {
        blocks.add(b);
        drawables.add(b);
    }

    private void buildPath(String startBlockId, String endBlockId, Direction d) {
        Path p = new Path(getBlockByID(startBlockId), getBlockByID(endBlockId), d);
        paths.add(p);
        p.getStartBlock().addPath(p);
    }

    private void buildTrack(int x1, int y1, int x2, int y2, List<Path> paths) {
        Track t = new Track(x1, y1, x2, y2);
        if(paths.size() > 0 || paths != null) {
            for(Path p : paths) {
                p.addTrack(t);
            }
        }
        drawables.add(t);
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
