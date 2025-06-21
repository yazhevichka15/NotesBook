package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.notionclone.utils.WindowResizer;

public class SettingsController {
    @FXML
    private AnchorPane settingsRoot;

    @FXML
    private Button lightTheme;

    @FXML
    private Button darkTheme;

    @FXML
    private Button closeButton;

    private AnchorPane settingsContainer;
    private Button newNoteButton;
    private AnchorPane settingsPage;
    private AnchorPane mainRoot;
    private AnchorPane noteRoot;

    public void setSettingsContainer(AnchorPane container){ this.settingsContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }
    public void setSettingsPage(AnchorPane settingsPage){ this.settingsPage = settingsPage; }
    public void setMainRoot(AnchorPane mainRoot) { this.mainRoot = mainRoot; }
    public void setNoteRoot(AnchorPane noteRoot) { this.noteRoot = noteRoot; }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> CloseSettingsPanel());
        darkTheme.setOnAction(event -> changeToDarkTheme());
        lightTheme.setOnAction(event -> changeToLightTheme());
        toggleResize.setOnAction(event -> toggleResizing());
    }

    public void openSettingsPanel(){
        settingsContainer.getChildren().clear();
        settingsContainer.getChildren().add(settingsPage);

        settingsContainer.setVisible(true);
        settingsContainer.toFront();
        newNoteButton.setVisible(false);
    }

    public void changeToLightTheme(){
        settingsRoot.getStylesheets().clear();
        mainRoot.getStylesheets().clear();
        noteRoot.getStylesheets().clear();
        settingsRoot.getStylesheets().add(getClass().getResource("/styles/lightTheme/settings-style.css").toExternalForm());
        mainRoot.getStylesheets().add(getClass().getResource("/styles/lightTheme/main-style.css").toExternalForm());
        noteRoot.getStylesheets().add(getClass().getResource("/styles/lightTheme/notes-style.css").toExternalForm());
    }

    public void changeToDarkTheme(){
        settingsRoot.getStylesheets().clear();
        mainRoot.getStylesheets().clear();
        noteRoot.getStylesheets().clear();
        settingsRoot.getStylesheets().add(getClass().getResource("/styles/darkTheme/settings-style.css").toExternalForm());
        mainRoot.getStylesheets().add(getClass().getResource("/styles/darkTheme/main-style.css").toExternalForm());
        noteRoot.getStylesheets().add(getClass().getResource("/styles/darkTheme/notes-style.css").toExternalForm());
    }

    private void CloseSettingsPanel(){
        settingsContainer.setVisible(false);
        newNoteButton.setVisible(true);
    }

    private Stage primaryStage;
    private boolean resizingEnabled = true;

    @FXML
    private Button toggleResize;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void toggleResizing() {
        resizingEnabled = !resizingEnabled;

        if (resizingEnabled) {
            WindowResizer.makeResizable(primaryStage, primaryStage.getScene());
            toggleResize.setText("Изменение размера: ВКЛ");
        } else {
            // Просто убираем обработчики
            primaryStage.getScene().setOnMouseMoved(null);
            primaryStage.getScene().setOnMouseDragged(null);
            primaryStage.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
            toggleResize.setText("Изменение размера: ВЫКЛ");
        }
    }
}
