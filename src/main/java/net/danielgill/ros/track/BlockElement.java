package net.danielgill.ros.track;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.block.SignalledBlock;

public class BlockElement extends Element {
    private int x;
    private int y;
    private Direction direction;
    private Block block;
    private Entity entity;

    public BlockElement(int x, int y, Direction direction, Block block) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.block = block;
    }

    @Override
    public void draw() {
        if(block instanceof SignalledBlock) {
            ((SignalledBlock) block).drawSignal(x, y, direction);
        }

        Line line;
        if(direction == Direction.EAST) {
            line = new Line(x, y, x - 40, y);
            line.setStrokeWidth(20);
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        } else if(direction == Direction.WEST) {
            line = new Line(x, y, x + 40, y);
            line.setStrokeWidth(20);
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        } else if(direction == Direction.NORTH) {
            line = new Line(x, y, x, y + 40);
            line.setStrokeWidth(20);
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        } else if(direction == Direction.SOUTH) {
            line = new Line(x, y, x, y - 40);
            line.setStrokeWidth(20);
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            FXGL.entityBuilder().at(0,0).view(line).buildAndAttach();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Block getBlock() {
        return block;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void update() {
        if(entity != null) {
            FXGL.getGameWorld().removeEntity(entity);
        }
        if(block.isOccupied()) {
            Text t = new Text(block.getOccupantId());
            t.setFill(Color.WHITE);
            t.setX(x - 34);
            t.setY(y + 4);
            t.setFont(Font.font("monospace"));
            entity = FXGL.entityBuilder().view(t).buildAndAttach();
        }
    }
}
