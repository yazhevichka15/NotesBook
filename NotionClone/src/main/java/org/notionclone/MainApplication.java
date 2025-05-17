package org.notionclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.notionclone.controller.MenuController;
import org.notionclone.view.GenerateNotesRefMain;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainXML.fxml"));

        double windowWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double windowHeight = Screen.getPrimary().getVisualBounds().getHeight();

        Scene scene = new Scene(fxmlLoader.load(), windowWidth * 0.675, windowHeight * 0.8);

        stage.setTitle("NotesApp");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

        MenuController menuController = fxmlLoader.getController();
        menuController.initializeNotes(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}