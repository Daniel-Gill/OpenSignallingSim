package net.danielgill.ros.track;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.shape.Line;
import net.danielgill.ros.block.Block;

public class BlockElement extends Element {
    private int x;
    private int y;
    private Direction direction;
    private Block block;

    public BlockElement(int x, int y, Direction direction, Block block) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.block = block;
    }

    @Override
    public void draw() {
        block.getSignal().draw(x, y, direction);

        Line line;
        if(direction == Direction.EAST) {
            line = new Line(x, y, x - 40, y);
            line.setStrokeWidth(20);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        }
    }
}