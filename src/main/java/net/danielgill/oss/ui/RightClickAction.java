package net.danielgill.oss.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import net.danielgill.oss.App;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.railway.Railway;

public class RightClickAction extends UserAction {
    public RightClickAction(String name) {
        super(name);
    }

    @Override
    protected void onActionBegin() {
        Railway r = App.railway;

        Point2D lastPos = FXGL.getInput().getMousePositionUI();
        Block b = r.getBlockAt(lastPos);

        if(b == null) {
            return;
        }

        b.clearPath();
    }
}
