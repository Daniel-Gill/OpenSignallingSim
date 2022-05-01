package net.danielgill.oss.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.railway.Railway;

public class RightClickAction extends UserAction {
    private Railway r;

    public RightClickAction(String name, Railway r) {
        super(name);
        this.r = r;
    }

    @Override
    protected void onActionBegin() {
        Point2D lastPos = FXGL.getInput().getMousePositionUI();
        Block b = r.getBlockAt(lastPos);

        if(b == null) {
            return;
        }

        b.clearPath();
    }
}
