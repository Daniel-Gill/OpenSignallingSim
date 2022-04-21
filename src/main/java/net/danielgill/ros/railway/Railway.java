package net.danielgill.ros.railway;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.ros.block.Block;
import net.danielgill.ros.path.Path;
import net.danielgill.ros.signal.FourAspectSignal;
import net.danielgill.ros.signal.Signal;
import net.danielgill.ros.signal.TwoAspectSignal;
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
        buildBlock("1", new FourAspectSignal(), 100, 100, Direction.EAST);
        buildBlock("2", new FourAspectSignal(), 240, 100, Direction.EAST);
        buildBlock("3", new FourAspectSignal(), 380, 100, Direction.EAST);
        buildBlock("4", new FourAspectSignal(), 520, 100, Direction.EAST);
        buildBlock("5", new FourAspectSignal(), 660, 100, Direction.EAST);
        buildBlock("6", new FourAspectSignal(), 800, 100, Direction.EAST);
    }

    private void buildBlock(String id, Signal signal, int x, int y, Direction direction) {
        Block b = new Block(id, signal);
        blocks.add(b);
        elements.add(new BlockElement(x, y, direction, b));
    }

    private void buildPaths() {
        buildPath("1", "2");
        buildPath("2", "3");
        buildPath("3", "4");
        buildPath("4", "5");
        buildPath("5", "6");
    }

    private void buildPath(String startBlockId, String endBlockId) {
        Path p = new Path(getBlockByID(startBlockId), getBlockByID(endBlockId));
        paths.add(p);
        p.getStartBlock().addFowardBlock(p.getEndBlock());
    }

    private void buildInterlocks() {
        
    }

    private void buildTracks() {
        List<Path> paths = new ArrayList<>();
        paths.add(getPathByID("1-2"));
        buildTrack(100, 100, 200, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("2-3"));
        buildTrack(240, 100, 340, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("3-4"));
        buildTrack(380, 100, 480, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("4-5"));
        buildTrack(520, 100, 620, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("5-6"));
        buildTrack(660, 100, 760, 100, paths);
    }

    private void buildTrack(int x1, int y1, int x2, int y2, List<Path> paths) {
        TrackElement t = new TrackElement(x1, y1, x2, y2);
        for(Path p : paths) {
            p.addTrackElement(t);
        }
        elements.add(t);
    }

    public void draw() {
        for (Element element : elements) {
            element.draw();
        }
        blocks.get(0).setPath(paths.get(0));
        blocks.get(1).setPath(paths.get(1));
        blocks.get(2).setPath(paths.get(2));
        blocks.get(4).setPath(paths.get(4));
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
}
