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
    private AnchorPane settingsContainer;

    @FXML
    public AnchorPane notesContainer;

    @FXML
    private Button newNoteButton;

    @FXML
    private Button settingsButton;

    private ArrayList<NoteUnit> listOfNotes;
    private NoteSimple currentNote;

    // noteContainer
    private AnchorPane notePage;
    private NoteController noteController;

    // settingContainer
    private AnchorPane settingsPage;
    private SettingsController settingsController;

    @FXML
    private void initialize() throws IOException {
        initializePages();

        newNoteButton.setOnAction(event -> openNotePanel());
        settingsButton.setOnAction(event -> openSettingsPanel());
    }

    private void initializePages() throws IOException {
        // noteContainer
        FXMLLoader noteLoader = new FXMLLoader(getClass().getResource("/org/notionclone/newNote.fxml"));
        this.notePage = noteLoader.load();

        this.noteController = noteLoader.getController();

        // settingContainer
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/org/notionclone/settings.fxml"));
        this.settingsPage = settingsLoader.load();

        this.settingsController = settingsLoader.getController();
    }

    public void initializeNotes() {
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

    private void openSettingsPanel(){
        settingsController.setSettingsContainer(settingsContainer);
        settingsController.setNewNoteButton(newNoteButton);

        settingsContainer.getChildren().clear();
        settingsContainer.getChildren().add(settingsPage);

        settingsContainer.setVisible(true);
        settingsContainer.toFront();
        newNoteButton.setVisible(false);
    }
}
