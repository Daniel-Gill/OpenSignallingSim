package net.danielgill.ros.railway;

import java.util.ArrayList;
import java.util.List;

import net.danielgill.ros.block.Block;
import net.danielgill.ros.signal.TwoAspectSignal;
import net.danielgill.ros.track.BlockElement;
import net.danielgill.ros.track.Direction;
import net.danielgill.ros.track.Element;
import net.danielgill.ros.track.TrackElement;

public class Railway {
    private List<Block> blocks;
    private List<Element> elements;

    public Railway() {
        blocks = new ArrayList<>();
        elements = new ArrayList<>();
    }

    /**
     * Temp method to generate a railway.
     */
    public void build() {
        elements.add(new TrackElement(100, 100, 140, 100));
        elements.add(new TrackElement(140, 100, 240, 100));
        elements.add(new TrackElement(140, 100, 180, 140));
        elements.add(new TrackElement(180, 140, 240, 140));

        Block block = new Block("1", new TwoAspectSignal());
        elements.add(new BlockElement(100, 100, Direction.EAST, block));
    }

    public void draw() {
        for (Element element : elements) {
            element.draw();
        }
    }
}
