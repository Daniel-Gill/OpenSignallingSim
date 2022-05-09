package net.danielgill.oss;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.ui.UI;
import com.github.cliftonlabs.json_simple.JsonException;

import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import net.danielgill.oss.railway.ParseRailway;
import net.danielgill.oss.railway.Railway;
import net.danielgill.oss.time.Clock;
import net.danielgill.oss.time.Time;
import net.danielgill.oss.timetable.Schedule;
import net.danielgill.oss.timetable.Timetable;
import net.danielgill.oss.timetable.event.EntryEvent;
import net.danielgill.oss.ui.LeftClickAction;
import net.danielgill.oss.ui.MainController;
import net.danielgill.oss.ui.RightClickAction;

public class App extends GameApplication {
    public static Clock clock;
    public static Railway railway;
    public static Timetable ttb;

    public static Text text;

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
        ttb = new Timetable(new Time(7, 0, 0));
        clock = new Clock(ttb.getStartTime());

        Schedule s = new Schedule("1A02");
        s.addEvent(new EntryEvent(new Time(7, 1, 5), railway.getBlockByID("1")));
        ttb.addSchedule(s);
        ttb.createTrains();
    }

    @Override
    protected void initInput() {
        railway = new Railway();
        Input input = FXGL.getInput();
        registerInputs(input);
    }

    private void registerInputs(Input input) {
        input.addAction(new RightClickAction("Right Click"), MouseButton.SECONDARY);
        input.addAction(new LeftClickAction("Left Click"), MouseButton.PRIMARY);
    }

    @Override
    protected void initUI() {
        UI ui = FXGL.getAssetLoader().loadUI("main.fxml", new MainController());
        FXGL.getGameScene().addUI(ui);

        text = new Text();
        text.setTranslateX(5);
        text.setTranslateY(40);
        text.textProperty().bind(FXGL.getWorldProperties().stringProperty("textHint"));

        FXGL.getGameScene().addUINode(text);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("textHint", "TEST");
    }

    public static void loadRoute(File file) throws IOException, JsonException {
        FXGL.getGameScene().clearGameViews();
        railway = ParseRailway.parseRailway(file);
        railway.draw();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
