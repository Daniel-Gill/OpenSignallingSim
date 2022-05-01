package net.danielgill.oss;

import java.io.File;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.ui.UI;

import javafx.scene.input.MouseButton;
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
        input.addAction(new RightClickAction("Right Click", App.railway), MouseButton.SECONDARY);
        input.addAction(new LeftClickAction("Left Click", App.railway), MouseButton.PRIMARY);
    }

    @Override
    protected void initUI() {
        UI ui = FXGL.getAssetLoader().loadUI("main.fxml", new MainController());
        FXGL.getGameScene().addUI(ui);
    }

    public static void loadRoute(File file) {
        railway = ParseRailway.parseRailway(file);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
