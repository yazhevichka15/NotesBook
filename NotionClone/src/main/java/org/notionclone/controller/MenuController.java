package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Path;

import static org.notionclone.model.NoteFileManager.*;

import org.notionclone.model.NoteUnits.*;

public class MenuController {

    @FXML
    public AnchorPane noteContainer;

    @FXML
    private void initialize() {
        try{
            FXMLLoader noteLoader = new FXMLLoader(getClass().getResource("/org/notionclone/newNote.fxml"));
            AnchorPane notePage = noteLoader.load();
            noteContainer.getChildren().add(notePage);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void openNotePanel() {
        try {
            Path notePath = createNoteFile();
            System.out.println("Создана новая заметка по пути: " + notePath);

            NoteSimple currentNote = new NoteSimple(notePath, "");

            noteContainer.setVisible(true);

            System.out.println(currentNote.getFilePath());
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }
}
