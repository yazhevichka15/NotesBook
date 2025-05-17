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

//    private ArrayList<NoteUnit> listOfNotes;
    private NoteSimple currentNote;

    // controllers
    private NoteController noteController;
    private SettingsController settingsController;

    @FXML
    private void initialize() throws IOException {
        initializePages();

        newNoteButton.setOnAction(event -> noteController.CreateNewNote());
        settingsButton.setOnAction(event -> settingsController.openSettingsPanel());
    }

    private void initializePages() throws IOException {
        // noteContainer
        FXMLLoader noteLoader = new FXMLLoader(getClass().getResource("/org/notionclone/newNote.fxml"));
        AnchorPane notePage = noteLoader.load();

        this.noteController = noteLoader.getController();

        noteController.setNoteContainer(noteContainer);
        noteController.setNewNoteButton(newNoteButton);
        noteController.setNotePage(notePage);

        // settingContainer
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/org/notionclone/settings.fxml"));
        AnchorPane settingsPage = settingsLoader.load();

        this.settingsController = settingsLoader.getController();

        settingsController.setSettingsContainer(settingsContainer);
        settingsController.setNewNoteButton(newNoteButton);
        settingsController.setSettingsPage(settingsPage);
    }

    public void initializeNotes() {
        GenerateNotesRefMain generateNotesRefMain = new GenerateNotesRefMain(noteController, notesContainer);
//        listOfNotes = generateNotesRefMain.getListOfNotes();

        try{
            generateNotesRefMain.generateNote();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openNotePanel() {

    }
}
