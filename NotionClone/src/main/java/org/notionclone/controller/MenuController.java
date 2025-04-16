package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.notionclone.model.NoteFileManager.*;

import org.notionclone.model.NoteUnits.*;

public class MenuController {

    @FXML
    public AnchorPane noteContainer;

    @FXML
    private Button newNoteButton;

    private ArrayList<NoteUnit> listOfNotes;
    private NoteSimple currentNote;

    public void setListOfNotes(ArrayList<NoteUnit> listOfNotes){
        this.listOfNotes = listOfNotes;
    };

    @FXML
    private void initialize() {
        newNoteButton.setOnAction(event -> openNotePanel());
    }

    private void openNotePanel() {
        try {
            Path notePath = createNoteFile();
            System.out.println("Создана новая заметка по пути: " + notePath);

            NoteSimple noteTemp = new NoteSimple(notePath, "");
            currentNote = noteTemp;
//            listOfNotes.add(noteTemp); - не тот тип заметок

            FXMLLoader noteLoader = new FXMLLoader(getClass().getResource("/org/notionclone/newNote.fxml"));
            AnchorPane notePage = noteLoader.load();

            NoteController noteController = noteLoader.getController();
            noteController.setNoteContainer(noteContainer);
            noteController.setNewNoteButton(newNoteButton);
            noteController.setCurrentNote(currentNote);

            noteContainer.getChildren().clear();
            noteContainer.getChildren().add(notePage);

            noteContainer.setVisible(true);
            noteContainer.toFront();
            newNoteButton.setVisible(false);
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }
}
