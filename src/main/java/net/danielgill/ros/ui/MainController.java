package net.danielgill.ros.ui;

import com.almasb.fxgl.ui.UIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController implements UIController {

    @FXML
    private Label labelTest;

    @Override
    public void init() {
        labelTest.setText("Hello World!");
    }
    
}
