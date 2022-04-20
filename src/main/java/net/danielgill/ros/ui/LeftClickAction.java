package net.danielgill.ros.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import net.danielgill.ros.graphics.Grid;
import net.danielgill.ros.track.StraightTrackElement;

public class LeftClickAction extends UserAction {
    private static Grid grid = Grid.getInstance();

    public LeftClickAction(String name) {
        super(name);
    }

    @Override
    protected void onActionBegin() {
        
    }

    @Override
    protected void onAction() {
        Point2D mousePos = FXGL.getInput().getMousePositionWorld();
        int x = (int) mousePos.getX() / grid.GRID_SIZE;
        int y = (int) mousePos.getY() / grid.GRID_SIZE;
        grid.addElement(new StraightTrackElement(x, y)); //TODO: get selected element type
    }

    @Override
    protected void onActionEnd() {

    }
}
