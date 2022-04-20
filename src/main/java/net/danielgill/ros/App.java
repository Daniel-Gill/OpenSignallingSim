package net.danielgill.ros;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputModifier;

import javafx.scene.input.MouseButton;
import net.danielgill.ros.graphics.Grid;
import net.danielgill.ros.track.StraightTrackElement;
import net.danielgill.ros.ui.DragAction;
import net.danielgill.ros.ui.LeftClickAction;
import net.danielgill.ros.ui.RightClickAction;

public class App extends GameApplication {
    private static Grid grid = Grid.getInstance();

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
        grid.addElement(new StraightTrackElement(11, 10));
        grid.addElement(new StraightTrackElement(10, 10));
        grid.addElement(new StraightTrackElement(10, 11));
        grid.addElement(new StraightTrackElement(12, 10));
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        registerInputs(input);
    }

    private void registerInputs(Input input) {
        input.addAction(new DragAction("Drag"), MouseButton.SECONDARY, InputModifier.CTRL);
        input.addAction(new RightClickAction("Right Click"), MouseButton.SECONDARY);
        input.addAction(new LeftClickAction("Left Click"), MouseButton.PRIMARY);
    }

    @Override
    protected void initUI() {
        //UI ui = FXGL.getAssetLoader().loadUI("main.fxml", new MainController());
        //FXGL.getGameScene().addUI(ui);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
