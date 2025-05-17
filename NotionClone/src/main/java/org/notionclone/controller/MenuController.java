package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.notionclone.model.NoteFileManager.*;

import org.notionclone.model.NoteUnits.*;
import org.notionclone.view.GenerateNotesRefMain;

public class MenuController {

    @FXML
    public AnchorPane noteContainer;

    @FXML
    public AnchorPane notesContainer;

    @FXML
    private Button newNoteButton;

    private ArrayList<NoteUnit> listOfNotes;
    private NoteSimple currentNote;

    @FXML
    private void initialize() {
        newNoteButton.setOnAction(event -> openNotePanel());
    }

    public void initializeNotes(Scene scene) {
        GenerateNotesRefMain generateNotesRefMain = new GenerateNotesRefMain(notesContainer);
        listOfNotes = generateNotesRefMain.getListOfNotes();

        try{
            generateNotesRefMain.generateNote();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openNotePanel() {
        try {
            Path notePath = createNoteFile();
            System.out.println("Создана новая заметка по пути: " + notePath);

            NoteSimple noteTemp = new NoteSimple(notePath, "");
            currentNote = noteTemp;
//            System.out.println(listOfNotes.get(1).getFilePath()); ПУТЬ
            listOfNotes.add(noteTemp);

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
