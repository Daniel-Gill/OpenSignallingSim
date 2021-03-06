package net.danielgill.oss.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import net.danielgill.oss.App;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.path.Path;
import net.danielgill.oss.railway.Railway;

public class LeftClickAction extends UserAction {
    private Block block;

    public LeftClickAction(String name) {
        super(name);
        this.block = null;
    }
    
    @Override
    protected void onActionBegin() {
        Railway r = App.railway;

        Point2D lastPos = FXGL.getInput().getMousePositionUI();
        Block b = r.getBlockAt(lastPos);

        if(b == null) {
            this.block = null;
            return;
        }

        if(this.block == null) {
            this.block = b;
            return;
        }

        if(this.block == b) {
            this.block = null;
            return;
        }

        Path p = r.getPathByID(this.block.getId() + "-" + b.getId());
        this.block.setPath(p);
        this.block.update();
        this.block = null;
    }

    @Override
    protected void onAction() {
        
    }

    @Override
    protected void onActionEnd() {

    }
}
