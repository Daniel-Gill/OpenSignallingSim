package net.danielgill.ros.railway;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.ros.block.Block;
import net.danielgill.ros.path.Path;
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
        buildBlock("1", new TwoAspectSignal(), 100, 100, Direction.EAST);
        buildBlock("2", new TwoAspectSignal(), 280, 140, Direction.EAST);
        buildBlock("3", new TwoAspectSignal(), 280, 100, Direction.EAST);
    }

    private void buildBlock(String id, Signal signal, int x, int y, Direction direction) {
        Block b = new Block(id, signal);
        blocks.add(b);
        elements.add(new BlockElement(x, y, direction, b));
    }

    private void buildPaths() {
        buildPath("1", "2");
        buildPath("1", "3");
    }

    private void buildPath(String startBlockId, String endBlockId) {
        Path p = new Path(getBlockByID(startBlockId), getBlockByID(endBlockId));
        paths.add(p);
    }

    private void buildInterlocks() {
        getPathByID("1-2").addInterlock(getPathByID("1-3"));
        getPathByID("1-3").addInterlock(getPathByID("1-2"));
    }

    private void buildTracks() {
        List<Path> paths = new ArrayList<>();
        paths.add(getPathByID("1-2"));
        paths.add(getPathByID("1-3"));
        buildTrack(100, 100, 140, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("1-2"));
        buildTrack(140, 100, 240, 100, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("1-3"));
        buildTrack(140, 100, 180, 140, paths);

        paths = new ArrayList<>();
        paths.add(getPathByID("1-3"));
        buildTrack(180, 140, 240, 140, paths);
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
