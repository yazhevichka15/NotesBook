package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SettingsController {
    @FXML
    public Button closeButton;

    private AnchorPane settingsContainer;
    private Button newNoteButton;
    private AnchorPane settingsPage;

    public void setSettingsContainer(AnchorPane container){ this.settingsContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }
    public void setSettingsPage(AnchorPane settingsPage){ this.settingsPage = settingsPage; }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> CloseSettingsPanel());
    }

    public void openSettingsPanel(){
        settingsContainer.getChildren().clear();
        settingsContainer.getChildren().add(settingsPage);

        settingsContainer.setVisible(true);
        settingsContainer.toFront();
        newNoteButton.setVisible(false);
    }

    private void CloseSettingsPanel(){
        settingsContainer.setVisible(false);
        newNoteButton.setVisible(true);
    }
}
