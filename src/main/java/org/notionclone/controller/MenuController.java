package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @FXML
    private Button minimizeButton;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button closeButton;

    private Stage primaryStage;

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

//        noteController.SetupListenerToFind(searchBar);
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

    public void initializeNotes() throws IOException {
        GenerateNotesRefMain generateNotesRefMain = new GenerateNotesRefMain(noteController, notesContainer);

        AtomicBoolean favouriteRender = new AtomicBoolean(false);

        generateNotesRefMain.generateNote(favouriteRender.get());

        mainButton.setOnAction(event -> {
            try{
                favouriteRender.set(false);

                generateNotesRefMain.generateNote(favouriteRender.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        favouriteButton.setOnAction(event -> {
            try{
                favouriteRender.set(true);

                generateNotesRefMain.generateNote(favouriteRender.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        noteController.getCloseButton().setOnAction(event -> {
            try{
                generateNotesRefMain.generateNote(favouriteRender.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            noteController.CloseNotePanel();
        });

//        Context menu to reload page. Can't remove because of method generateNote.
        ContextMenu contextMenu = new ContextMenu();

        MenuItem reloadPage = new MenuItem("Перезагрузить");
        reloadPage.setOnAction(event -> {
            try {
                generateNotesRefMain.generateNote(favouriteRender.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        contextMenu.getItems().add(reloadPage);

        notesContainer.setOnContextMenuRequested(event ->
                contextMenu.show(notesContainer, event.getScreenX(), event.getScreenY())
        );
    }

    public void passStageToSettings(Stage stage) {
        settingsController.setPrimaryStage(stage);
    }
}
