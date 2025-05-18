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
import org.notionclone.model.NoteFileManager;
import org.notionclone.model.NoteUnits.*;

import static org.notionclone.model.NoteFileManager.createNoteFile;

public class NoteController{
    @FXML
    private AnchorPane noteRoot;

    @FXML
    private TextField textFiledSimpleNote;

    @FXML
    private TextArea textAreaSimpleNote;

    @FXML
    private Button closeButton;

    private AnchorPane noteContainer;
    private Button newNoteButton;
    private AnchorPane notePage;
    private NoteSimple currentNote;

    // Listeners
    private javafx.beans.value.ChangeListener<String> contentListener;
    private javafx.beans.value.ChangeListener<String> titleListener;

    public void setNoteContainer(AnchorPane container){ this.noteContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }
    public void setNotePage(AnchorPane notePage){ this.notePage = notePage; }

    public AnchorPane getNoteRoot() { return noteRoot; }

    @FXML
    private void initialize(){
        closeButton.setOnAction(event -> CloseNotePanel());
    }

    public void CreateNewNote() {
        try {
            Path notePath = createNoteFile();
            System.out.println("Создана новая заметка по пути: " + notePath);

            currentNote = new NoteSimple(notePath, "");

            OpenNotePanel(currentNote);

            textFiledSimpleNote.clear();
            textAreaSimpleNote.clear();
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
                    currentNote = new NoteSimple(notePath, Files.readString(notePath));

                    OpenNotePanel(currentNote);

                    textFiledSimpleNote.setText(noteTitle);
                    textAreaSimpleNote.setText(currentNote.getContent()); // Может просто Files.readString(notePath)
                    // и без создания экземпляра currentNote
                }
            }
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }

    public void OpenNotePanel(NoteSimple currentNote) throws IOException {
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

    private void SetupListeners(NoteSimple currentNote){
        if (contentListener != null)
            textAreaSimpleNote.textProperty().removeListener(contentListener);
        if (titleListener != null)
            textFiledSimpleNote.textProperty().removeListener(titleListener);

        contentListener = (observable, oldValue, newValue) -> {
            if (currentNote != null) {
                currentNote.setContent(newValue);
                try {
                    currentNote.saveContent();
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        };

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

        textAreaSimpleNote.textProperty().addListener(contentListener);
        textFiledSimpleNote.textProperty().addListener(titleListener);
    }
}
