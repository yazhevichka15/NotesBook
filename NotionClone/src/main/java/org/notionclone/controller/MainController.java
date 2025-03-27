package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Path;

import static org.notionclone.model.NoteFileManager.*;
import org.notionclone.model.NoteUnits.*;

public class MainController {
    @FXML
    private AnchorPane notePane;

    @FXML
    private Button closeButton;

    @FXML
    public TextField textFiledSimpleNote;

    @FXML
    public TextArea textAreaSimpleNote;

    private NoteSimple currentNote;

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> notePane.setVisible(false));

        textAreaSimpleNote.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentNote != null) {
                currentNote.setContent(newValue);
                try {
                    currentNote.saveContent();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void openNotePanel() {
        try {
            Path notePath = createNoteFile();
            System.out.println("Создана новая заметка по пути: " + notePath);

            currentNote = new NoteSimple("Новая заметка", notePath, "");

            textAreaSimpleNote.clear();
            textFiledSimpleNote.clear();

            notePane.setVisible(true);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
