package org.notionclone.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
<<<<<<< HEAD
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

=======
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
>>>>>>> Branch-to-test
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

<<<<<<< HEAD
=======
import javafx.stage.FileChooser;
>>>>>>> Branch-to-test
import org.notionclone.model.Listeners;
import org.notionclone.model.NoteFileManager;
import org.notionclone.model.NoteUnits.NoteUnit;
import org.notionclone.model.MarkdownHandler;

import static org.notionclone.model.NoteFileManager.createNoteFile;

public class NoteController{
    @FXML
    private AnchorPane noteRoot;

    @FXML
    private AnchorPane toolsPanel;

    @FXML
    private TextField textFieldSimpleNote;

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

<<<<<<< HEAD
=======
    @FXML
    private Button boldButton;
    @FXML
    private Button italicButton;
    @FXML
    private Button headerButton;
    @FXML
    private Button listButton;
    @FXML
    private Button addButton;

>>>>>>> Branch-to-test
    private AnchorPane noteContainer;
    private Button newNoteButton;
    private AnchorPane notePage;
    private NoteUnit currentNote;

    public void setNoteContainer(AnchorPane container){ this.noteContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }
    public void setNotePage(AnchorPane notePage){ this.notePage = notePage; }

    public AnchorPane getNoteRoot() { return noteRoot; }

    @FXML
    private void initialize(){
        closeButton.setOnAction(event -> CloseNotePanel());
        viewButton.setOnAction(event -> editModToggle(false));
        editButton.setOnAction(event -> editModToggle(true));

<<<<<<< HEAD
        textAreaSimpleNote.setOnContextMenuRequested(Event::consume);
=======
        ContextMenu textContextMenu = createTextContextMenu();
        textAreaSimpleNote.setContextMenu(textContextMenu);
//        textAreaSimpleNote.setOnContextMenuRequested(Event::consume);

        boldButton.setOnAction(e -> wrapSelectedText("**", "**"));
        italicButton.setOnAction(e -> wrapSelectedText("*", "*"));
        headerButton.setOnAction(e -> insertAtCursor("# ", ""));
        listButton.setOnAction(e -> insertAtCursor("- ", ""));
        addButton.setOnAction(e -> showInsertMenu());
>>>>>>> Branch-to-test
    }

    public void OpenNotePanel(NoteUnit currentNote) throws IOException {
        noteContainer.getChildren().clear();
        noteContainer.getChildren().add(notePage);

        noteContainer.setVisible(true);
        noteContainer.toFront();
        newNoteButton.setVisible(false);

        Listeners.SetupListenerTextField(currentNote, textFieldSimpleNote);
        Listeners.SetupListenerTextArea(currentNote, textAreaSimpleNote, markdownView);
        Listeners.SetupMouseActions(textAreaSimpleNote);
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

            textFieldSimpleNote.clear();
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

                    textFieldSimpleNote.setText(noteTitle);
                    textAreaSimpleNote.setText(currentNote.getContent());

                    String contentToRender = MarkdownHandler.RenderMd(currentNote.getContent());
<<<<<<< HEAD
                    markdownView.getEngine().loadContent(contentToRender);
=======

                    markdownView.getEngine().loadContent(contentToRender, "text/html");
>>>>>>> Branch-to-test
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
<<<<<<< HEAD
}
=======

    private void wrapSelectedText(String prefix, String suffix) {
        String selectedText = textAreaSimpleNote.getSelectedText();
        if (selectedText != null && !selectedText.isEmpty()) {
            int start = textAreaSimpleNote.getSelection().getStart();
            int end = textAreaSimpleNote.getSelection().getEnd();

            textAreaSimpleNote.replaceText(start, end, prefix + selectedText + suffix);
            textAreaSimpleNote.selectRange(start, start + prefix.length() + selectedText.length() + suffix.length());
        } else {
            int pos = textAreaSimpleNote.getCaretPosition();
            textAreaSimpleNote.insertText(pos, prefix + suffix);
            textAreaSimpleNote.positionCaret(pos + prefix.length());
        }
    }

    private void insertAtCursor(String prefix, String suffix) {
        int pos = textAreaSimpleNote.getCaretPosition();
        textAreaSimpleNote.insertText(pos, prefix + suffix);
        textAreaSimpleNote.positionCaret(pos + prefix.length());
    }

    private void showInsertMenu() {
        ContextMenu insertMenu = new ContextMenu();

        MenuItem imageItem = new MenuItem("Вставить картинку");
        imageItem.setOnAction(e -> insertImage());

        MenuItem tableItem = new MenuItem("Вставить таблицу");
        tableItem.setOnAction(e -> insertTable());

        insertMenu.getItems().addAll(imageItem, tableItem);
        insertMenu.show(addButton, Side.BOTTOM, 0, 0);
    }

    private void insertImage() {
        TextInputDialog dialog = new TextInputDialog("https://example.com/image.png");
        dialog.setTitle("Вставить изображение");
        dialog.setHeaderText("Введите URL изображения");
        dialog.setContentText("Ссылка:");

        dialog.showAndWait().ifPresent(url -> {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                insertAtCursor("![Описание](" + url + ")", "");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неверный формат URL");
                alert.setContentText("Ссылка должна начинаться с http:// или https://");
                alert.showAndWait();
            }
        });
    }

    private void insertTable() {
        String tableTemplate =
                "| Заголовок 1 | Заголовок 2 |\n" +
                        "|-------------|-------------|\n" +
                        "| Ячейка 1    | Ячейка 2    |\n";

        insertAtCursor(tableTemplate, "");
    }

    private ContextMenu createTextContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem boldItem = new MenuItem("Жирный");
        boldItem.setOnAction(e -> wrapSelectedText("**", "**"));

        MenuItem italicItem = new MenuItem("Курсив");
        italicItem.setOnAction(e -> wrapSelectedText("*", "*"));

        MenuItem headerItem = new MenuItem("Заголовок");
        headerItem.setOnAction(e -> insertAtCursor("# ", ""));

        MenuItem listItem = new MenuItem("Список");
        listItem.setOnAction(e -> insertAtCursor("- ", ""));

        SeparatorMenuItem separator = new SeparatorMenuItem();

        MenuItem imageItem = new MenuItem("Вставить картинку");
        imageItem.setOnAction(e -> insertImage());

        MenuItem tableItem = new MenuItem("Вставить таблицу");
        tableItem.setOnAction(e -> insertTable());

        contextMenu.getItems().addAll(
                boldItem,
                italicItem,
                headerItem,
                listItem,
                separator,
                imageItem,
                tableItem
        );

        return contextMenu;
    }
}



>>>>>>> Branch-to-test
