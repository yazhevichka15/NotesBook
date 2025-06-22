package org.notionclone.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import javafx.stage.FileChooser;
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



    private AnchorPane noteContainer;
    private Button newNoteButton;
    private AnchorPane notePage;
    private NoteUnit currentNote;

    public void setNoteContainer(AnchorPane container){ this.noteContainer = container; }
    public void setNewNoteButton(Button newNoteButton){ this.newNoteButton = newNoteButton; }
    public void setNotePage(AnchorPane notePage){ this.notePage = notePage; }

    public AnchorPane getNoteRoot() { return noteRoot; }

    public Button getCloseButton() { return closeButton; }

    @FXML
    private void initialize(){
//        closeButton.setOnAction(event -> CloseNotePanel());
        viewButton.setOnAction(event -> editModToggle(false));
        editButton.setOnAction(event -> editModToggle(true));


        ContextMenu textContextMenu = createTextContextMenu();
        textAreaSimpleNote.setContextMenu(textContextMenu);
//        textAreaSimpleNote.setOnContextMenuRequested(Event::consume);

        boldButton.setOnAction(e -> wrapSelectedText("**", "**"));
        italicButton.setOnAction(e -> wrapSelectedText("*", "*"));
        headerButton.setOnAction(e -> insertAtCursor("# ", ""));
        listButton.setOnAction(e -> showListInsertMenu());
        addButton.setOnAction(e -> showInsertMenu());
    }

    @FXML
    private void switchToViewMode() {

        textAreaSimpleNote.setVisible(false);
        markdownView.setVisible(true);

        viewButton.getStyleClass().removeAll("active");
        editButton.getStyleClass().removeAll("active");

        if (!viewButton.getStyleClass().contains("active")) {
            viewButton.getStyleClass().add("active");
        }
    }

    @FXML
    private void switchToEditMode() {

        textAreaSimpleNote.setVisible(true);
        markdownView.setVisible(false);

        viewButton.getStyleClass().removeAll("active");
        editButton.getStyleClass().removeAll("active");

        if (!editButton.getStyleClass().contains("active")) {
            editButton.getStyleClass().add("active");
        }

    }

    public void OpenNotePanel(NoteUnit currentNote) throws IOException {
        noteContainer.getChildren().clear();
        noteContainer.getChildren().add(notePage);

        noteContainer.setVisible(true);
        noteContainer.toFront();
        newNoteButton.setVisible(false);

        Listeners.SetupListenerTextField(currentNote, textFieldSimpleNote);
        Listeners.SetupListenerTextArea(currentNote, textAreaSimpleNote, markdownView);
//        Listeners.SetupMouseActions(textAreaSimpleNote);
    }

    public void CloseNotePanel(){
        noteContainer.setVisible(false);
        newNoteButton.setVisible(true);
    }

    public void CreateNewNote() {
        System.out.println("CreateNewNote called");
        try {
            Path notePath = createNoteFile();

            currentNote = new NoteUnit(notePath, "");

            OpenNotePanel(currentNote);

            textFieldSimpleNote.clear();
            textAreaSimpleNote.clear();
            markdownView.getEngine().loadContent("");
            editModToggle(true);
        } catch (IOException exception) {
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }

    public void OpenExistedNote(Pane pane){
        System.out.println("CreateNewNote called");
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
                    String styledHtml = """
                        <html>
                        <head>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                font-size: 16pt;
                                color: rgba(77,77,77,1);
                                padding: 20px;
                            }
                        </style>
                        </head>
                        <body>%s</body>
                        </html>
                        """.formatted(contentToRender);

                    markdownView.getEngine().loadContent(styledHtml, "text/html");
                    switchToViewMode();
                    editModToggle(false);
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
            // Включен режим редактирования
            textAreaSimpleNote.setVisible(true);
            markdownView.setVisible(false);
            toolsPanel.setVisible(true);

            switchToEditMode();
            textFieldSimpleNote.setEditable(true);
            textFieldSimpleNote.setDisable(false);
        } else {
            // Включен режим просмотра
            textAreaSimpleNote.setVisible(false);
            markdownView.setVisible(true);
            toolsPanel.setVisible(false);

            switchToViewMode();
            textFieldSimpleNote.setEditable(false);
            textFieldSimpleNote.setDisable(true);
        }
    }

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(textAreaSimpleNote.getScene().getWindow());

        if (selectedFile != null) {
            try {
                Path imagesDir = Path.of("data/images");
                if (!Files.exists(imagesDir)) {
                    Files.createDirectories(imagesDir);
                }

                Path newImagePath = imagesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), newImagePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                String url = newImagePath.toFile().toURI().toString();

                insertAtCursor("![Описание](" + url + ")", "");
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка вставки изображения");
                alert.setHeaderText("Не удалось вставить изображение");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
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

        MenuItem copyItem = new MenuItem("Копировать");
        copyItem.setOnAction(e -> textAreaSimpleNote.copy());

        MenuItem pasteItem = new MenuItem("Вставить");
        pasteItem.setOnAction(e -> textAreaSimpleNote.paste());

        MenuItem cutItem = new MenuItem("Вырезать");
        cutItem.setOnAction(e -> textAreaSimpleNote.cut());

        SeparatorMenuItem firstSeparator = new SeparatorMenuItem();

        MenuItem boldItem = new MenuItem("Жирный");
        boldItem.setOnAction(e -> wrapSelectedText("**", "**"));

        MenuItem italicItem = new MenuItem("Курсив");
        italicItem.setOnAction(e -> wrapSelectedText("*", "*"));

        MenuItem headerItem = new MenuItem("Заголовок");
        headerItem.setOnAction(e -> insertAtCursor("# ", ""));

        Menu listSubMenu = new Menu("Список");

        MenuItem bulletList = new MenuItem("Маркированный");
        bulletList.setOnAction(e -> insertAtCursor("- ", ""));

        MenuItem numberedList = new MenuItem("Нумерованный");
        numberedList.setOnAction(e -> insertNextNumberedItem());

        SeparatorMenuItem secondSeparator = new SeparatorMenuItem();

        MenuItem imageItem = new MenuItem("Вставить картинку");
        imageItem.setOnAction(e -> insertImage());

        MenuItem tableItem = new MenuItem("Вставить таблицу");
        tableItem.setOnAction(e -> insertTable());

        contextMenu.getItems().addAll(
                copyItem,
                pasteItem,
                cutItem,
                firstSeparator,
                boldItem,
                italicItem,
                headerItem,
                bulletList,
                numberedList,
                secondSeparator,
                imageItem,
                tableItem
        );

        return contextMenu;
    }

    private int numberedListCounter = 1;
    private boolean continueNumberedList = false;

    private void insertNextNumberedItem() {
        int caretPos = textAreaSimpleNote.getCaretPosition();
        String textBefore = textAreaSimpleNote.getText(0, caretPos);

        String[] lines = textBefore.split("\n");

        int lastIndex = lines.length - 1;
        while (lastIndex >= 0 && lines[lastIndex].trim().isEmpty()) {
            lastIndex--;
        }

        if (lastIndex >= 0 && lines[lastIndex].trim().matches("^\\d+\\.\\s.*")) {
            String lastLine = lines[lastIndex].trim();
            String[] parts = lastLine.split("\\.", 2);
            try {
                numberedListCounter = Integer.parseInt(parts[0].trim()) + 1;
            } catch (NumberFormatException e) {
                numberedListCounter = 1;
            }
        } else {
            numberedListCounter = 1;
        }

        insertAtCursor(numberedListCounter + ". ", "");
        numberedListCounter++;
    }



    @FXML
    private void showListInsertMenu() {
        ContextMenu listMenu = new ContextMenu();

        MenuItem bulletList = new MenuItem("Маркированный");
        bulletList.setOnAction(e -> insertAtCursor("- ", ""));

        MenuItem numberedList = new MenuItem("Нумерованный");
        numberedList.setOnAction(e -> insertNextNumberedItem());

        listMenu.getItems().addAll(bulletList, numberedList);
        listMenu.show(listButton, Side.BOTTOM, 0, 0);
    }

}



