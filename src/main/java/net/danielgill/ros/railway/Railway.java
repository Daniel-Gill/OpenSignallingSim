package net.danielgill.ros.railway;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.block.SignalledBlock;
import net.danielgill.ros.block.TwoWaySignalledBlock;
import net.danielgill.ros.path.Path;
import net.danielgill.ros.signal.FourAspectSignal;
import net.danielgill.ros.signal.ShuntSignal;
import net.danielgill.ros.signal.Signal;
import net.danielgill.ros.track.BlockElement;
import net.danielgill.ros.track.Direction;
import net.danielgill.ros.track.Element;
import net.danielgill.ros.track.TrackElement;

public class Railway {
    private List<Block> blocks;
    private List<Path> paths;
    private List<Element> elements;

    public Railway() {
        blocks = new ArrayList<>();
        paths = new ArrayList<>();
        elements = new ArrayList<>();
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
        buildBlock(new TwoWaySignalledBlock("7", new FourAspectSignal(30, 0), new FourAspectSignal(-30, 0)), 200, 200, Direction.EAST);
        buildBlock(new SignalledBlock("2", new FourAspectSignal(0, 0)), 250, 100, Direction.EAST);
        buildBlock(new SignalledBlock("4", new FourAspectSignal(0, 0)), 350, 150, Direction.WEST);
        buildBlock(new SignalledBlock("3", new FourAspectSignal(0, 0)), 400, 100, Direction.EAST);
        buildBlock(new TwoWaySignalledBlock("5", new FourAspectSignal(0, 0), new ShuntSignal(0, 0)), 210, 150, Direction.WEST);
        buildBlock(new SignalledBlock("1", new FourAspectSignal(0, 0)), 140, 100, Direction.EAST);
        buildBlock(new SignalledBlock("6", new FourAspectSignal(0, 0)), 100, 150, Direction.WEST);
    }

    private void buildPaths() {
        buildPath("1", "2", Direction.EAST);
        buildPath("2", "3", Direction.EAST);
        buildPath("4", "5", Direction.EAST);
        buildPath("5", "6", Direction.EAST);
        
        buildPath("5", "3", Direction.WEST);
    }

    private void buildInterlocks() {
        getPathByID("4-5").addInterlock(getPathByID("5-3"));
        getPathByID("2-3").addInterlock(getPathByID("5-3"));
    }

    private void buildTracks() {
        List<Path> paths = new ArrayList<>();
        //paths.add(getPathByID("1-2"));
        buildTrack(100, 200, 130, 200, paths);

        paths = new ArrayList<>();
        //paths.add(getPathByID("1-2"));
        buildTrack(130, 200, 230, 200, paths);

        paths = new ArrayList<>();
        //paths.add(getPathByID("1-2"));
        buildTrack(230, 200, 275, 200, paths);

        paths = new ArrayList<>();
        //paths.add(getPathByID("1-2"));
        buildTrack(275, 200, 300, 150, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-5"));
        buildTrack(300, 150, 320, 150, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-5"));
        buildTrack(320, 150, 350, 150, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("5-3"));
        buildTrack(320, 150, 340, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("2-3"));
        paths.add(getPathByID("5-3"));
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

    private void buildBlock(Block b, int x, int y, Direction direction) {
        blocks.add(b);
        elements.add(new BlockElement(x, y, direction, b));
    }

    private void buildPath(String startBlockId, String endBlockId, Direction d) {
        Path p = new Path(getBlockByID(startBlockId), getBlockByID(endBlockId), d);
        paths.add(p);
        p.getStartBlock().addFowardBlock(p.getEndBlock());
    }

    private void buildTrack(int x1, int y1, int x2, int y2, List<Path> paths) {
        TrackElement t = new TrackElement(x1, y1, x2, y2);
        if(paths.size() > 0 || paths != null) {
            for(Path p : paths) {
                p.addTrackElement(t);
            }
        }
        elements.add(t);
    }

    public void draw() {
        elements.forEach(e -> e.draw());
        elements.forEach(e -> e.update());
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
        for(Element b : elements) {
            if(!(b instanceof BlockElement)) {
                continue;
            }
            BlockElement be = (BlockElement) b;
            
            if(be.getDirection() == Direction.EAST) {
                if(pos.getX() >= be.getX() - 40 && pos.getX() <= be.getX() && be.getY() - 10 <= pos.getY() && pos.getY() <= be.getY() + 10) {
                    return be.getBlock();
                }
            } else if(be.getDirection() == Direction.WEST) {
                if(pos.getX() <= be.getX() + 40 && pos.getX() >= be.getX() && be.getY() - 10 <= pos.getY() && pos.getY() <= be.getY() + 10) {
                    return be.getBlock();
                }
            } else if(be.getDirection() == Direction.NORTH) {
                if(pos.getY() <= be.getY() + 40 && pos.getY() >= be.getY() && be.getX() - 10 <= pos.getX() && pos.getX() <= be.getX() + 10) {
                    return be.getBlock();
                }
            } else if(be.getDirection() == Direction.SOUTH) {
                if(pos.getY() >= be.getY() - 40 && pos.getY() <= be.getY() && be.getX() - 10 <= pos.getX() && pos.getX() <= be.getX() + 10) {
                    return be.getBlock();
                }
            }
        }
        return null;
    }
}
