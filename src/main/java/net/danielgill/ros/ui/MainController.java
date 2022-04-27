package net.danielgill.ros.ui;

import com.almasb.fxgl.ui.UIController;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import net.danielgill.ros.App;

public class MainController implements UIController {
    @FXML private Menu clock;
    @FXML private MenuItem start;
    @FXML private MenuItem pause;

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
    
}
