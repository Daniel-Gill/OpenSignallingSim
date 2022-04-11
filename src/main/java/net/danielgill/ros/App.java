package net.danielgill.ros;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;

import javafx.scene.input.MouseButton;
import net.danielgill.ros.graphics.Grid;
import net.danielgill.ros.track.StraightTrackElement;
import net.danielgill.ros.ui.DragAction;

public class App extends GameApplication {
    public static Grid GRID = Grid.getInstance();

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
        GRID.addElement(new StraightTrackElement(11, 10));
        GRID.addElement(new StraightTrackElement(10, 10));
        GRID.addElement(new StraightTrackElement(10, 11));
        GRID.addElement(new StraightTrackElement(12, 10));
        GRID.draw();
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
        //UI ui = FXGL.getAssetLoader().loadUI("main.fxml", new MainController());
        //FXGL.getGameScene().addUI(ui);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
