package net.danielgill.ros.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import net.danielgill.ros.graphics.Grid;

public class RightClickAction extends UserAction {
    private static Grid grid = Grid.getInstance();
    private Point2D lastPos;

    public RightClickAction(String name) {
        super(name);
    }

    @Override
    protected void onActionBegin() {
        
    }

    @Override
    protected void onAction() {
        lastPos = FXGL.getInput().getMousePositionWorld();
        grid.removeEntityAtPos(lastPos);
    }

    @Override
    protected void onActionEnd() {

    }
    
}
