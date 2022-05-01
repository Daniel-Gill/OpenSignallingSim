package net.danielgill.oss.ui;

import com.almasb.fxgl.ui.UIController;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import net.danielgill.oss.App;

public class MainController implements UIController {
    @FXML private Menu clock;
    @FXML private MenuItem start;
    @FXML private MenuItem pause;

    @FXML private MenuItem loadRoute;
    @FXML private MenuItem loadTimetable;

    @Override
    public void init() {
        clock.setText(App.clock.getTime().toString());
        App.clock.runAtSecond(() -> {
            clock.setText(App.clock.getTime().toString());
        });
        
        start.setOnAction(value -> {
            App.clock.start();
        });

        pause.setOnAction(value -> {
            App.clock.pause();
        });
    }

    @FXML
    private void loadRoute() {
        FileChooser fc = new FileChooser();
    }
    
}
