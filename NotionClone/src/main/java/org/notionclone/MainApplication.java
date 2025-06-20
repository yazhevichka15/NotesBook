package org.notionclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.notionclone.controller.MenuController;

import java.awt.*;
import java.io.IOException;

public class MainApplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainXML.fxml"));

        double windowWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double windowHeight = Screen.getPrimary().getVisualBounds().getHeight();

        stage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(fxmlLoader.load(), windowWidth * 0.675, windowHeight * 0.8);

        MenuController menuController = fxmlLoader.getController();
        menuController.setPrimaryStage(stage);
        menuController.initializeNotes();

        javafx.scene.Node titleBar = scene.lookup("#titleBar");
        if (titleBar != null) {
            titleBar.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            titleBar.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
        }

        stage.setScene(scene);
        stage.show();

//        stage.initStyle(StageStyle.DECORATED);
//        stage.setTitle("NotesApp");
//        stage.setFullScreen(true);
//        stage.setScene(scene);
//        stage.show();

//        MenuController menuController = fxmlLoader.getController();
//        menuController.initializeNotes();
    }

    public static void main(String[] args) {
        launch();
    }
}