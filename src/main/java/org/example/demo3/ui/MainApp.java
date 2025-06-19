package org.example.demo3.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        PokerSimulatorUI ui = new PokerSimulatorUI();
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}
