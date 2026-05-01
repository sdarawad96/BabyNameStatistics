package edu.westga.comp2320.babynamestatistics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BabyNameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BabyNameApplication.class.getResource("babyname-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 645, 655);
        stage.setTitle("Baby Name Statistics by Sondus Darawad");
        stage.setScene(scene);
        stage.show();
    }
}
