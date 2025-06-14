package org.notionclone.model;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebView;
import org.notionclone.model.NoteUnits.NoteUnit;

import java.io.IOException;
import java.nio.file.Path;

public class Listeners {
    private static javafx.beans.value.ChangeListener<String> contentListener;
    private static javafx.beans.value.ChangeListener<String> titleListener;
    private javafx.beans.value.ChangeListener<String> searchBarListener;

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
                System.out.println(newValue);
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

    public static void SetupMouseActions(TextArea textAreaSimpleNote){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem copyItem = new MenuItem("Копировать");
        MenuItem pasteItem = new MenuItem("Вставить");
        MenuItem cutItem = new MenuItem("Вырезать");

        copyItem.setOnAction(event -> textAreaSimpleNote.copy());
        pasteItem.setOnAction(event -> textAreaSimpleNote.paste());
        cutItem.setOnAction(event -> textAreaSimpleNote.cut());

        contextMenu.getItems().addAll(copyItem, pasteItem, cutItem);

        textAreaSimpleNote.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY){
                contextMenu.show(textAreaSimpleNote, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
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
}
