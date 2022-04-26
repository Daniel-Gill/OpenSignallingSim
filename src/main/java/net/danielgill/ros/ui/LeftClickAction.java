package net.danielgill.ros.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.block.SignalledBlock;
import net.danielgill.ros.path.Path;
import net.danielgill.ros.railway.Railway;

public class LeftClickAction extends UserAction {
    private Railway r;
    private Block block;

    public LeftClickAction(String name, Railway r) {
        super(name);
        this.r = r;
        this.block = null;
    }
    
    @Override
    protected void onActionBegin() {
        Point2D lastPos = FXGL.getInput().getMousePositionUI();
        System.out.println("arg0: " + lastPos.getX() + " arg1: " + lastPos.getY());
        Block b = r.getBlockAt(lastPos);

        if(b == null) {
            this.block = null;
            return;
        }

        System.out.println("Clicked on block " + b.getId());

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
        if(this.block instanceof SignalledBlock) {
            ((SignalledBlock) this.block).updateSignal();
        }
        this.block = null;
    }

    @Override
    protected void onAction() {
        
    }

    @Override
    protected void onActionEnd() {

    }
}
