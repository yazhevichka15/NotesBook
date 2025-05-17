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

    public void setNoteContainer(AnchorPane container){ this.noteContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }
    public void setNotePage(AnchorPane notePage){ this.notePage = notePage; }

    @FXML
    private void initialize(){
        closeButton.setOnAction(event -> CloseNotePanel());
    }

    public void OpenNotePanel(NoteSimple currentNote) throws IOException {
        noteContainer.getChildren().clear();
        noteContainer.getChildren().add(notePage);

        noteContainer.setVisible(true);
        noteContainer.toFront();
        newNoteButton.setVisible(false);

        textAreaSimpleNote.clear();
        textFiledSimpleNote.clear();

        textAreaSimpleNote.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentNote != null) {
                currentNote.setContent(newValue);
                try {
                    currentNote.saveContent();
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        textFiledSimpleNote.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentNote != null && newValue != null && !newValue.trim().isEmpty()){
                Path newFilePath = Path.of("data/notes/" + newValue.trim() + ".txt");
                try{
                    currentNote.saveFilePath(newFilePath);
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        });
    }

    private void CloseNotePanel(){
        noteContainer.setVisible(false);
        newNoteButton.setVisible(true);
    }
}
