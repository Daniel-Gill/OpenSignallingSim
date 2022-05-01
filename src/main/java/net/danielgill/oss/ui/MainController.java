package net.danielgill.oss.ui;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.UIController;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private void loadRoute() throws URISyntaxException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("OpenSignallingSim route file (.rte)", "*.rte"));
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        File jarFile = new File(url.toURI());
        fc.setInitialDirectory(new File(jarFile.getParentFile().getAbsolutePath()));
        Stage stage = FXGL.getPrimaryStage();
        File file = fc.showOpenDialog(stage);
        if(file != null) {
            App.loadRoute(file);
        }
    }
    
}
