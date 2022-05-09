package net.danielgill.oss.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import net.danielgill.oss.App;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.railway.Railway;

public class RightClickAction extends UserAction {
    Point2D lastPos;

    public RightClickAction(String name) {
        super(name);
    }

    @Override
    protected void onActionBegin() {
        Railway r = App.railway;

        lastPos = FXGL.getInput().getMousePositionUI();
        Block b = r.getBlockAt(lastPos);

        if(b == null) {
            return;
        }
        
        b.clearPath();
    }

    @Override
    protected void onAction() {
        lastPos = FXGL.getInput().getMousePositionUI();
        int x = (int) lastPos.getX();
        int y = (int) lastPos.getY();
        FXGL.set("textHint", "[" + x + ", " + y + "]");
    }
}
