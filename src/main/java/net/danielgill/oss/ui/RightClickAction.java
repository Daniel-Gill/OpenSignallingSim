package net.danielgill.oss.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import net.danielgill.oss.App;
import net.danielgill.oss.block.Block;
import net.danielgill.oss.railway.Railway;

public class RightClickAction extends UserAction {
    private Point2D lastPos;
    private Block lastBlock;

    // used to determine if the block has changed
    private Block prevBlock = null;

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
        lastBlock = b;
        
        b.clearPath();
    }

    @Override
    protected void onAction() {
        
    }

    @Override
    protected void onActionEnd() {
        lastPos = FXGL.getInput().getMousePositionUI();
        int x = Math.round((int) lastPos.getX() / 5) * 5;
        int y = Math.round((int) lastPos.getY() / 5) * 5;
        if(prevBlock != lastBlock) {
            FXGL.set("textHint", "Cleared path from block " + lastBlock.getId() + " [" + x + ", " + y + "]");
        } else if(prevBlock == null || prevBlock == lastBlock) {
            FXGL.set("textHint", "[" + x + ", " + y + "]");
        }
        prevBlock = lastBlock;
    }
}
