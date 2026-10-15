package com.stardewtracker;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

        scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());

        stage.setTitle("Stardew Community Center Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}