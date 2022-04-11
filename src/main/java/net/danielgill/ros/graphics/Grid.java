package net.danielgill.ros.graphics;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import net.danielgill.ros.track.TrackElement;

public class Grid {
    public final int GRID_SIZE = 30;
    public final int GRID_WIDTH = 500;
    public final int GRID_HEIGHT = 500;

    private static Grid instance = null;
    private ArrayList<TrackElement> gridElements = new ArrayList<>();

    private Grid() {
        
    }

    public static Grid getInstance() {
        if(instance == null) {
            instance = new Grid();
        }
        return instance;
    }

    public void addElement(TrackElement element) {
        gridElements.add(element);
    }

    public void draw() {
        for(TrackElement te : gridElements) {
            Entity e = FXGL.entityBuilder().at(te.X_LOC * GRID_SIZE, te.Y_LOC * GRID_SIZE).view(te.getImageString()).buildAndAttach();
            e.setScaleX(1);
        }
    }
}
