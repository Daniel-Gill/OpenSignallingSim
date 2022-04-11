package net.danielgill.ros;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.UI;

import net.danielgill.ros.ui.MainController;

public class App extends GameApplication {
    public static final int GRID_WIDTH = 500;
    public static final int GRID_HEIGHT = 500;
    public static final int GRID_SIZE = 10;

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
    protected void initUI() {
        UI ui = FXGL.getAssetLoader().loadUI("main.fxml", new MainController());
        FXGL.getGameScene().addUI(ui);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
