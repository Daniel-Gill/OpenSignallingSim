package net.danielgill.ros;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;

import javafx.scene.input.MouseButton;
import net.danielgill.ros.railway.Railway;
import net.danielgill.ros.ui.LeftClickAction;
import net.danielgill.ros.ui.RightClickAction;

public class App extends GameApplication {
    protected Railway railway;

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
        railway.build();
        railway.draw();
    }

    @Override
    protected void initInput() {
        railway = new Railway();
        Input input = FXGL.getInput();
        registerInputs(input);
    }

    private void registerInputs(Input input) {
        input.addAction(new RightClickAction("Right Click", this.railway), MouseButton.SECONDARY);
        input.addAction(new LeftClickAction("Left Click", this.railway), MouseButton.PRIMARY);
    }

    @Override
    protected void initUI() {
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
