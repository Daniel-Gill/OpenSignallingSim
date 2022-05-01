package net.danielgill.oss.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;

public class DragAction extends UserAction {
    private Point2D lastPos;

    public DragAction(String name) {
        super(name);
    }
    
    @Override
    protected void onActionBegin() {
        lastPos = FXGL.getInput().getMousePositionWorld();
    }

    @Override
    protected void onAction() {
        Point2D currentPos = FXGL.getInput().getMousePositionWorld();
        double deltaX = currentPos.getX() - lastPos.getX();
        double deltaY = currentPos.getY() - lastPos.getY();
        FXGL.getGameWorld().getEntities().forEach(e -> e.translate(deltaX, deltaY));
        lastPos = currentPos;
    }

    @Override
    protected void onActionEnd() {

    }
}
