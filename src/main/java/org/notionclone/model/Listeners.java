package org.notionclone.model;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebView;
import org.notionclone.model.NoteUnits.NoteUnit;
import org.notionclone.view.GenerateNotesRefMain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Listeners {
    private static javafx.beans.value.ChangeListener<String> contentListener;
    private static javafx.beans.value.ChangeListener<String> titleListener;
    private static javafx.beans.value.ChangeListener<String> searchBarListener;

    public static void SetupListenerTextField(NoteUnit currentNote, TextField textFieldSimpleNote){
        if (titleListener != null)
            textFieldSimpleNote.textProperty().removeListener(titleListener);

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

        textFieldSimpleNote.textProperty().addListener(titleListener);
    }

    public static void SetupListenerTextArea (NoteUnit currentNote, TextArea textAreaSimpleNote, WebView markdownView){
        if (contentListener != null)
            textAreaSimpleNote.textProperty().removeListener(contentListener);

        contentListener = (observable, oldValue, newValue) -> {
            if (currentNote != null) {
                currentNote.setContent(newValue);
                try {
                    currentNote.saveContent();
                    String contentToRender = MarkdownHandler.RenderMd(newValue);
                    markdownView.getEngine().loadContent(contentToRender);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        };

        textAreaSimpleNote.textProperty().addListener(contentListener);
    }

        public static void SetupListenerToFind(TextField searchBar, AtomicBoolean favouriteRender, GenerateNotesRefMain generateNotesRefMain){
        if (searchBarListener != null){
            searchBar.textProperty().removeListener(searchBarListener);
        }

        searchBarListener = ((observableValue, oldValue, newValue) -> {
            try{
                generateNotesRefMain.generateNote(favouriteRender.get(), searchBar.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        searchBar.textProperty().addListener(searchBarListener);
    }
}
