package org.notionclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainXML.fxml"));

        double windowWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double windowHeight = Screen.getPrimary().getVisualBounds().getHeight();

        Scene scene = new Scene(fxmlLoader.load(), windowWidth * 0.675, windowHeight * 0.8);

        stage.setMinWidth(windowWidth * 0.45);
        stage.setMinHeight(windowHeight * 0.55);

        stage.setTitle("Notion Clone");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}