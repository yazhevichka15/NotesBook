package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import org.notionclone.view.GenerateNotesRefMain;

public class MenuController {
    @FXML
    private AnchorPane mainRoot;

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

    @FXML
    private Button minApp;

    // controllers
    private NoteController noteController;
    private SettingsController settingsController;

    @FXML
    private void initialize() throws IOException {
        initializePages();

        newNoteButton.setOnAction(event -> noteController.CreateNewNote());
        settingsButton.setOnAction(event -> settingsController.openSettingsPanel());
        minApp.setOnAction(event -> ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true));
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
        settingsController.setMainRoot(mainRoot);
        settingsController.setNoteRoot(noteController.getNoteRoot());
    }

    public void initializeNotes() {
        GenerateNotesRefMain generateNotesRefMain = new GenerateNotesRefMain(noteController, notesContainer);

        try{
            generateNotesRefMain.generateNote();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
