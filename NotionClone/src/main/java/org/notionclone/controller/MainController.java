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
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        textFiledSimpleNote.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentNote != null && newValue != null && !newValue.trim().isEmpty()){
                Path newFilePath = Path.of("data/notes/" + newValue.trim() + ".txt");
                try{
                    currentNote.saveFilePath(newFilePath);
                    System.out.println(currentNote.getFilePath());
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        });
    }

    @FXML
    private void openNotePanel() {
        try {
            Path notePath = createNoteFile();
            System.out.println("Создана новая заметка по пути: " + notePath);

            currentNote = new NoteSimple(notePath, "");

            textAreaSimpleNote.clear();
            textFiledSimpleNote.clear();

            notePane.setVisible(true);

            System.out.println(currentNote.getFilePath());
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }
}
