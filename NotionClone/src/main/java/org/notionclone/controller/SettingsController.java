package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SettingsController {
    @FXML
    public Button closeButton;

    private AnchorPane settingsContainer;
    private Button newNoteButton;

    public void setSettingsContainer(AnchorPane container){ this.settingsContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> CloseSettingsPanel());
    }

    private void CloseSettingsPanel(){
        settingsContainer.setVisible(false);
        newNoteButton.setVisible(true);
    }
}
