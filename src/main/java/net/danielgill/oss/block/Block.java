package net.danielgill.oss.block;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.danielgill.oss.location.Location;
import net.danielgill.oss.path.Path;
import net.danielgill.oss.train.Train;
import net.danielgill.oss.ui.Direction;
import net.danielgill.oss.ui.Drawable;
import net.danielgill.oss.ui.Selectable;

public abstract class Block implements Drawable, Selectable, Exitable {
    protected String id;

    //location on screen
    protected int x;
    protected int y;
    protected Direction direction;

    //train reference text entity
    protected Entity entity;

    // trains in block
    protected boolean occupied;
    protected boolean earlyOccupied;
    protected Train train;

    // timetable stuff
    protected Location location;

    // path from block
    protected Path path;
    protected Block nextBlock;

    //block context
    protected ArrayList<Block> forwardBlocks;
    protected ArrayList<Block> backBlocks;

    public Block(String id, int x, int y, Direction direction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.occupied = false;

        forwardBlocks = new ArrayList<>();
        backBlocks = new ArrayList<>();
    }

    public Block(String id, int x, int y, Direction direction, Location location) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.occupied = false;

        forwardBlocks = new ArrayList<>();
        backBlocks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy(Train t) {
        occupied = true;
        earlyOccupied = false;
        train = t;
        this.update();
    }

    public void occupyEarly(Train t) {
        occupied = true;
        earlyOccupied = true;
        train = t;
        this.update();
    }

    public void setNotEarly() {
        earlyOccupied = false;
        this.update();
    }

    public void unoccupy() {
        occupied = false;
        earlyOccupied = false;
        train = null;
        this.update();
    }

    public void setPath(Path path) {
        if(path == null) {
            System.err.println("No path to be set for " + this.id);
            return;
        }
        if(!forwardBlocks.contains(path.getEndBlock())) {
            System.err.println(path.getStartBlock().id + " is not a forward block of " + this.id);
            return;
        }
        if(this.path != null) {
            System.err.println("Path already set for " + this.id);
            return;
        }
        if(!path.canActivate()) {
            System.err.println("Path cannot be activated for " + this.id);
            return;
        }
        
        this.path = path;
        this.nextBlock = path.getEndBlock();
        this.path.activate();
        this.update();
    }

    public void clearPath() {
        if(this.path == null) {
            return;
        }
        if(this.occupied) {
            System.err.println("Cannot clear path for occupied block " + this.id);
            return;
        }
        this.path.deactivate();
        this.path = null;
        this.nextBlock = null;
        this.update();
    }

    public Path getPath() {
        return path;
    }

    public boolean hasPath() {
        return path != null;
    }

    public void addPath(Path path) {
        this.addFowardBlock(path.getEndBlock());
        path.getEndBlock().addBackBlock(this);
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public String getOccupantId() {
        if(occupied) {
            return train.getId();
        } else {
            return "NULL";
        }
    }

    protected void addFowardBlock(Block block) {
        forwardBlocks.add(block);
    }

    protected void removeForwardBlock(Block block) {
        forwardBlocks.remove(block);
    }

    protected void addBackBlock(Block block) {
        backBlocks.add(block);
    }

    protected void removeBackBlock(Block block) {
        backBlocks.remove(block);
    }

    @Override
    public void draw() {
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

    @Override
    public void update() {
        if(entity != null) {
            FXGL.getGameWorld().removeEntity(entity);
        }
        if(this.isOccupied()) {
            Text t = new Text(this.getOccupantId());
            if(this.earlyOccupied) {
                t.setFill(Color.YELLOW);
            } else {
                t.setFill(Color.WHITE);
            }
            t.setX(x - 34);
            t.setY(y + 4);
            t.setFont(Font.font("monospace"));
            entity = FXGL.entityBuilder().view(t).buildAndAttach();
        }
    }

    @Override
    public boolean isSelected(Point2D pos) {
        if(this.direction == Direction.EAST) {
            if(pos.getX() >= this.x - 40 && pos.getX() <= this.x && this.y - 10 <= pos.getY() && pos.getY() <= this.y + 10) {
                return true;
            }
        } else if(this.direction == Direction.WEST) {
            if(pos.getX() <= this.x + 40 && pos.getX() >= this.x && this.y - 10 <= pos.getY() && pos.getY() <= this.y + 10) {
                return true;
            }
        } else if(this.direction == Direction.NORTH) {
            if(pos.getY() <= this.y + 40 && pos.getY() >= this.y && this.x - 10 <= pos.getX() && pos.getX() <= this.x + 10) {
                return true;
            }
        } else if(this.direction == Direction.SOUTH) {
            if(pos.getY() >= this.y - 40 && pos.getY() <= this.y && this.x - 10 <= pos.getX() && pos.getX() <= this.x + 10) {
                return true;
            }
        }
        return false;
    }

}
