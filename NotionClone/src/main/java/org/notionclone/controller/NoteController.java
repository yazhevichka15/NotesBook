package org.notionclone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Path;

import javafx.scene.layout.AnchorPane;
import org.notionclone.model.NoteUnits.*;

import static org.notionclone.model.NoteFileManager.createNoteFile;

public class NoteController{
    @FXML
    public TextField textFiledSimpleNote;

    @FXML
    public Button closeButton;

    @FXML
    public TextArea textAreaSimpleNote;

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

    @FXML
    private void initialize(){
        closeButton.setOnAction(event -> CloseNotePanel());
    }

    public void CreateNewNote() {
        try {
            Path notePath = createNoteFile();
            System.out.println("Создана новая заметка по пути: " + notePath);

            currentNote = new NoteSimple(notePath, "");
//            System.out.println(listOfNotes.get(1).getFilePath()); ПУТЬ
//            listOfNotes.add(noteTemp);

            OpenNotePanel(currentNote);
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }

    }

    public void OpenExistedNote(){
        try{
            OpenNotePanel(currentNote);

            currentNote.setContent("123123");

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

        textAreaSimpleNote.clear();
        textFiledSimpleNote.clear();

        setupListeners(currentNote);
    }

    private void CloseNotePanel(){
        noteContainer.setVisible(false);
        newNoteButton.setVisible(true);
    }

    private void setupListeners(NoteSimple currentNote){
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
