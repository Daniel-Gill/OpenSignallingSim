package net.danielgill.ros;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputModifier;

import javafx.scene.input.MouseButton;
import net.danielgill.ros.block.Block;
import net.danielgill.ros.signal.FourAspectSignal;
import net.danielgill.ros.ui.DragAction;

public class App extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("railopsim");
        settings.setVersion("0.1");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setIntroEnabled(false);
        settings.setFullScreenAllowed(false);
        settings.setCloseConfirmation(false);
    }

    @Override
    protected void initGame() {
        
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        registerInputs(input);
    }

    private void registerInputs(Input input) {
        input.addAction(new DragAction("Drag"), MouseButton.SECONDARY);
    }

    @Override
    protected void initUI() {
        
    }

    public static void main(String[] args) {
        launch(args);
        Block b = new Block("1", new FourAspectSignal());
        Block b2 = new Block("2", new FourAspectSignal());
        Block b3 = new Block("3", new FourAspectSignal());
        Block b4 = new Block("4", new FourAspectSignal());
        b.addFowardBlock(b2);
        b2.addFowardBlock(b3);
        b3.addFowardBlock(b4);
        b2.setPath(b3);
        b3.setPath(b4);
        b.setPath(b2);
        System.out.println(b.getSignal().getAspect());
        System.out.println(b2.getSignal().getAspect());
        System.out.println(b3.getSignal().getAspect());
        System.out.println(b4.getSignal().getAspect());
        b2.setOccupied(true);
        System.out.println("");
        System.out.println(b.getSignal().getAspect());
        System.out.println(b2.getSignal().getAspect());
        System.out.println(b3.getSignal().getAspect());
        System.out.println(b4.getSignal().getAspect());
        b2.setOccupied(false);
        b3.setOccupied(true);
        System.out.println("");
        System.out.println(b.getSignal().getAspect());
        System.out.println(b2.getSignal().getAspect());
        System.out.println(b3.getSignal().getAspect());
        System.out.println(b4.getSignal().getAspect());
    }
}
