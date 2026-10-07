package com.stardewtracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

    @FXML 
    private Label welcomeLabel;

    @FXML 
    private void handleTestButton(){
        welcomeLabel.setText("FXML i Controller rade!");
    }
}
