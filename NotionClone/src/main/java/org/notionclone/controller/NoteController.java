package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Path;

import org.notionclone.model.NoteUnits.*;

public class NoteController {
    @FXML
    public TextField textFiledSimpleNote;

    @FXML
    public TextArea textAreaSimpleNote;

    private NoteSimple currentNote;

    private void initialize(){
        textAreaSimpleNote.clear();
        textFiledSimpleNote.clear();

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
}
