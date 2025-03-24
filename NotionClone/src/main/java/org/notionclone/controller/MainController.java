package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane notePane;

    @FXML
    private Button closeButton;

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> notePane.setVisible(false));
    }

    @FXML
    private void openNotePanel() {
        notePane.setVisible(true);
    }
}
