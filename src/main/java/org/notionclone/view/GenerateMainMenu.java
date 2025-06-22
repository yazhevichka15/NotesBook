package org.notionclone.view;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GenerateMainMenu {
    public static void generateMenu(Tab tabToAdd){
        AnchorPane menuContainer = new AnchorPane();
        tabToAdd.setContent(menuContainer);

        // Поле поиска
        TextField searchBar = new TextField();
        searchBar.setLayoutX(1109);
        searchBar.setLayoutY(45);
        searchBar.setPrefHeight(42);
        searchBar.setPrefWidth(260);
        searchBar.setId("searchBar");
        menuContainer.getChildren().add(searchBar);

        // ScrollPane + внутренний контейнер
        AnchorPane notesContainer = new AnchorPane();
        notesContainer.setPrefHeight(957);
        notesContainer.setPrefWidth(1582);
        notesContainer.getStyleClass().add("main-background");
        notesContainer.setId("notesContainer");

        ScrollPane notesScrollPane = new ScrollPane(notesContainer);
        notesScrollPane.setLayoutX(1);
        notesScrollPane.setLayoutY(142);
        notesScrollPane.setPrefHeight(958);
        notesScrollPane.setPrefWidth(1701);
        notesScrollPane.setFitToHeight(true);
        notesScrollPane.setId("notesScrollPane");
        menuContainer.getChildren().add(notesScrollPane);

        // Заголовок NotesBook
        Text title = new Text("NotesBook");
        title.setLayoutX(92);
        title.setLayoutY(84);
        title.setStyle("-fx-font: 50px 'Arial Bold';");
        title.getStyleClass().add("text-h1");
        menuContainer.getChildren().add(title);

        // noteContainer
        AnchorPane noteContainer = new AnchorPane();
        noteContainer.setLayoutX(65);
        noteContainer.setLayoutY(20);
        noteContainer.setPrefHeight(980);
        noteContainer.setPrefWidth(1480);
        noteContainer.setVisible(false);
        noteContainer.setId("noteContainer");
        noteContainer.getStyleClass().add("container");
        menuContainer.getChildren().add(noteContainer);

        // Кнопка "+"
        Button newNoteButton = new Button("+");
        newNoteButton.setLayoutX(1460);
        newNoteButton.setLayoutY(890);
        newNoteButton.setPrefHeight(100);
        newNoteButton.setPrefWidth(100);
        newNoteButton.setMinHeight(50);
        newNoteButton.setMinWidth(50);
        newNoteButton.setMaxHeight(100);
        newNoteButton.setMaxWidth(100);
        newNoteButton.setMnemonicParsing(false);
        newNoteButton.setId("newNoteButton");
        newNoteButton.setFont(Font.font("System Bold", 40));
        newNoteButton.getStyleClass().add("add-button");
        menuContainer.getChildren().add(newNoteButton);

        // ComboBox с фильтрами
        ComboBox<String> filterChoice = new ComboBox<>();
        filterChoice.setLayoutX(1395);
        filterChoice.setLayoutY(45);
        filterChoice.setPrefHeight(42);
        filterChoice.setPrefWidth(150);
        filterChoice.setId("filterChoice");

        filterChoice.setItems(FXCollections.observableArrayList(
                "По алфавиту ↓",
                "По алфавиту ↑",
                "По дате создания ↓",
                "По дате создания ↑"
        ));
        filterChoice.setValue("По алфавиту ↓");
        menuContainer.getChildren().add(filterChoice);
    }
}
