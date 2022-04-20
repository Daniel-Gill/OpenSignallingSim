package net.danielgill.ros.graphics;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.geometry.Point2D;
import net.danielgill.ros.track.TrackElement;

public class Grid {
    public final int GRID_SIZE = 30;
    public final int GRID_WIDTH = 500;
    public final int GRID_HEIGHT = 500;

    private static Grid instance = null;
    private ArrayList<TrackElement> gridElements = new ArrayList<>();
    private ArrayList<Entity> gridEntities = new ArrayList<>();

    private Grid() {
        
    }

    public static Grid getInstance() {
        if(instance == null) {
            instance = new Grid();
        }
        return instance;
    }

    public void addElement(TrackElement element) {
        draw(element);
    }

    public void removeEntityAtPos(Point2D pos) {
        for(Entity e : gridEntities) {
            boolean onX = e.getX() <= pos.getX() && e.getX() + GRID_SIZE >= pos.getX();
            boolean onY = e.getY() <= pos.getY() && e.getY() + GRID_SIZE >= pos.getY();
            if(onX && onY) {
                gridEntities.remove(e);
                FXGL.getGameWorld().removeEntity(e);

                int x = (int) pos.getX() / GRID_SIZE;
                int y = (int) pos.getY() / GRID_SIZE;
                removeElementAtPos(x, y);
                break;
            }
        }
    }

    private void removeElementAtPos(int x, int y) {
        for(TrackElement e : gridElements) {
            if(e.X_LOC == x && e.Y_LOC == y) {
                gridElements.remove(e);
                break;
            }
        }
    }

    public void drawAll() {
        for(TrackElement te : gridElements) {
            Entity e = FXGL.entityBuilder().at(te.X_LOC * GRID_SIZE, te.Y_LOC * GRID_SIZE).view(te.getImageString()).buildAndAttach();
            gridEntities.add(e);
        }
    }

    private void draw(TrackElement element) {
        if(elementExistsAtPos(element.X_LOC, element.Y_LOC)) {
            
        } else {
            gridElements.add(element);
            Entity e = FXGL.entityBuilder().at(element.X_LOC * GRID_SIZE, element.Y_LOC * GRID_SIZE).view(element.getImageString()).buildAndAttach();
            gridEntities.add(e);
        }
    }

    private boolean elementExistsAtPos(int x, int y) {
        for(TrackElement te : gridElements) {
            if(te.X_LOC == x && te.Y_LOC == y) {
                return true;
            }
        }
        return false;
    }
}
