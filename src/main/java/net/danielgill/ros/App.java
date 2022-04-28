package net.danielgill.ros;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.ui.UI;

import javafx.scene.input.MouseButton;
import net.danielgill.ros.railway.Railway;
import net.danielgill.ros.time.Clock;
import net.danielgill.ros.time.Time;
import net.danielgill.ros.timetable.Schedule;
import net.danielgill.ros.timetable.Timetable;
import net.danielgill.ros.timetable.event.EntryEvent;
import net.danielgill.ros.ui.LeftClickAction;
import net.danielgill.ros.ui.MainController;
import net.danielgill.ros.ui.RightClickAction;

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

    public static void main(String[] args) {
        launch(args);
    }
}
