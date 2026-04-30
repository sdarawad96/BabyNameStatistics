package edu.westga.comp2320.babynamestatistics.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BabyNameController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
