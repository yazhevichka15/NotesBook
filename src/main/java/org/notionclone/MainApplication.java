package org.notionclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.notionclone.controller.MenuController;
import org.notionclone.utils.WindowResizer;
import javafx.scene.image.Image;

import java.io.IOException;

public class MainApplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    private final double windowWidth = Screen.getPrimary().getVisualBounds().getWidth();
    private final double windowHeight = Screen.getPrimary().getVisualBounds().getHeight();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainXML.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);

        stage.initStyle(StageStyle.UNDECORATED);
        MenuController menuController = fxmlLoader.getController();
        menuController.setPrimaryStage(stage);
        menuController.passStageToSettings(stage);
        menuController.initializeNotes();

        Node titleBar = scene.lookup("#titleBar");
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

        stage.setX(0);
        stage.setY(0);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setScene(scene);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        WindowResizer.makeResizable(stage, scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}