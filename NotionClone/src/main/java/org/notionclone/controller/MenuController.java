package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
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
    private Button mainButton;

    @FXML
    private Button newNoteButton;

    @FXML
    private Button favouriteButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button minApp;

    @FXML
    private TextField searchBar;


    private Stage primaryStage;

    @FXML
    private Button minimizeButton;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button closeButton;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
        setupWindowControls();
    }

    private void setupWindowControls() {
        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));

        maximizeButton.setOnAction(e -> {
            if (primaryStage.isMaximized()) {
                primaryStage.setMaximized(false);
                maximizeButton.setText("□");
            } else {
                primaryStage.setMaximized(true);
                maximizeButton.setText("❐");
            }
        });

        closeButton.setOnAction(e -> primaryStage.close());
    }

    // controllers
    private NoteController noteController;
    private SettingsController settingsController;

    //others
    private double xPos = 0;
    private double yPos = 0;

    @FXML
    private void initialize() throws IOException {
        initializePages();

        newNoteButton.setOnAction(event -> noteController.CreateNewNote());
        settingsButton.setOnAction(event -> settingsController.openSettingsPanel());
        minApp.setOnAction(event -> ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true));

//        mainRoot.setOnMouseDragExited(event -> {
//
//        });
//
//        mainRoot.setOnMousePressed(event -> {
//
//        });

//        noteController.SetupListenerToFind(searchBar);
    }

//    @FXML
//    private void mouseDraggedMainRoot(MouseEvent event){
//        Stage stage = (Stage) mainRoot.getScene().getWindow();
//        stage.setY(event.getScreenY() - yPos);
//        stage.setX(event.getScreenX() - xPos);
//    }
//
//    @FXML
//    private void mousePressedMainRoot(MouseEvent event){
//        xPos = event.getScreenX();
//        yPos = event.getScreenY();
//    }

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

    public void initializeNotes() throws IOException {
        GenerateNotesRefMain generateNotesRefMain = new GenerateNotesRefMain(noteController, notesContainer);

        generateNotesRefMain.generateNote(false);

        mainButton.setOnAction(event -> {
            try{
                generateNotesRefMain.generateNote(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        favouriteButton.setOnAction(event -> {
            try{
                generateNotesRefMain.generateNote(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
