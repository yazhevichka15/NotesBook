package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import org.notionclone.model.NoteFileManager;
import org.notionclone.model.NoteUnits.NoteUnit;
import org.notionclone.model.markdownHandler;

import static org.notionclone.model.NoteFileManager.createNoteFile;

public class NoteController{
    @FXML
    private AnchorPane noteRoot;

    @FXML
    private AnchorPane toolsPanel;

    @FXML
    private TextField textFiledSimpleNote;

    @FXML
    private TextArea textAreaSimpleNote;

    @FXML
    private WebView markdownView;

    @FXML
    private Button viewButton;

    @FXML
    private Button editButton;

    @FXML
    private Button closeButton;

    private AnchorPane noteContainer;
    private Button newNoteButton;
    private AnchorPane notePage;
    private NoteUnit currentNote;

    // Listeners
    private javafx.beans.value.ChangeListener<String> contentListener;
    private javafx.beans.value.ChangeListener<String> titleListener;
    private javafx.beans.value.ChangeListener<String> searchBarListener;

    public void setNoteContainer(AnchorPane container){ this.noteContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }
    public void setNotePage(AnchorPane notePage){ this.notePage = notePage; }

    public AnchorPane getNoteRoot() { return noteRoot; }

    @FXML
    private void initialize(){
        closeButton.setOnAction(event -> CloseNotePanel());
        viewButton.setOnAction(event -> editModToggle(false));
        editButton.setOnAction(event -> editModToggle(true));
    }

    public void OpenNotePanel(NoteUnit currentNote) throws IOException {
        noteContainer.getChildren().clear();
        noteContainer.getChildren().add(notePage);

        noteContainer.setVisible(true);
        noteContainer.toFront();
        newNoteButton.setVisible(false);

        SetupListeners(currentNote);
    }

    private void CloseNotePanel(){
        noteContainer.setVisible(false);
        newNoteButton.setVisible(true);
    }

    public void CreateNewNote() {
        try {
            Path notePath = createNoteFile();

            currentNote = new NoteUnit(notePath, "");

            OpenNotePanel(currentNote);

            textFiledSimpleNote.clear();
            textAreaSimpleNote.clear();
            markdownView.getEngine().loadContent("");
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }

    public void OpenExistedNote(Pane pane){
        try{
            for (javafx.scene.Node node : pane.getChildren()) {
                if (node instanceof Text textNode) {
                    String noteTitle = textNode.getText().trim();
                    Path notePath = Path.of("data/notes/" + noteTitle + ".txt");
                    currentNote = new NoteUnit(notePath, Files.readString(notePath));

                    OpenNotePanel(currentNote);

                    textFiledSimpleNote.setText(noteTitle);
                    textAreaSimpleNote.setText(currentNote.getContent());

                    String contentToRender = markdownHandler.renderMd(currentNote.getContent());
                    markdownView.getEngine().loadContent(contentToRender);
                }
            }
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }

    public void DeleteNote(Pane pane){
        try{
            for (javafx.scene.Node node : pane.getChildren()){
                if (node instanceof Text textNode) {
                    String noteTitle = textNode.getText().trim();

                    NoteFileManager.deleteNoteFile(noteTitle);
                }
            }
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }

    private void editModToggle(Boolean action) {
        if (action) {
            textAreaSimpleNote.setVisible(true);
            markdownView.setVisible(false);
            toolsPanel.setVisible(true);
            editButton.setStyle("-fx-background-color: rgb(120, 120, 120);");
            viewButton.setStyle("-fx-background-color: rgb(210, 210, 210)");
        } else {
            textAreaSimpleNote.setVisible(false);
            markdownView.setVisible(true);
            toolsPanel.setVisible(false);
            editButton.setStyle("-fx-background-color: rgb(210, 210, 210)");
            viewButton.setStyle("-fx-background-color: rgb(120, 120, 120);");
        }
    }

//    public void SetupListenerToFind(TextField searchBar){
//        if (searchBarListener != null){
//            searchBar.textProperty().removeListener(searchBarListener);
//        }
//
//        searchBarListener = ((observableValue, oldValue, newValue) -> {
//
//        });
//
//        searchBar.textProperty().addListener(searchBarListener);
//    }

    private void SetupListeners(NoteUnit currentNote){
        if (titleListener != null)
            textFiledSimpleNote.textProperty().removeListener(titleListener);
        if (contentListener != null)
            textAreaSimpleNote.textProperty().removeListener(contentListener);

        titleListener = (observable, oldValue, newValue) -> {
            if (currentNote != null && newValue != null && !newValue.trim().isEmpty()){
                Path newFilePath = Path.of("data/notes/" + newValue.trim() + ".txt");
                try{
                    currentNote.saveFilePath(newFilePath);
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        };

        contentListener = (observable, oldValue, newValue) -> {
            if (currentNote != null) {
                currentNote.setContent(newValue);
                try {
                    currentNote.saveContent();
                    String contentToRender = markdownHandler.renderMd(newValue);
                    markdownView.getEngine().loadContent(contentToRender);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        };

        textFiledSimpleNote.textProperty().addListener(titleListener);
        textAreaSimpleNote.textProperty().addListener(contentListener);
    }
}
