package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SettingsController {
    @FXML
    private Button lightTheme;

    @FXML
    private Button darkTheme;

    @FXML
    private Button closeButton;

    private AnchorPane settingsContainer;
    private Button newNoteButton;
    private AnchorPane settingsPage;

    public void setSettingsContainer(AnchorPane container){ this.settingsContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }
    public void setSettingsPage(AnchorPane settingsPage){ this.settingsPage = settingsPage; }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> CloseSettingsPanel());
        darkTheme.setOnAction(event -> changeToDarkTheme());
        lightTheme.setOnAction(event -> changeToLightTheme());
    }

    public void openSettingsPanel(){
        settingsContainer.getChildren().clear();
        settingsContainer.getChildren().add(settingsPage);

        settingsContainer.setVisible(true);
        settingsContainer.toFront();
        newNoteButton.setVisible(false);
    }

    public void changeToLightTheme(){
        lightTheme.setStyle("-fx-background-color: rgba(0,0,0,0); -fx-text-fill: black;");
    }

    public void changeToDarkTheme(){
        darkTheme.setStyle("-fx-background-color: #444; -fx-text-fill: white;");
    }

    private void CloseSettingsPanel(){
        settingsContainer.setVisible(false);
        newNoteButton.setVisible(true);
    }
}
