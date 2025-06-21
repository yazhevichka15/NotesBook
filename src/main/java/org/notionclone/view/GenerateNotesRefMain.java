package org.notionclone.view;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import org.notionclone.controller.NoteController;
import org.notionclone.model.NoteUnits.NoteUnit;
import org.notionclone.model.NoteInformation;
import org.notionclone.model.MarkdownHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.file.StandardWatchEventKinds.*;

public class GenerateNotesRefMain {
    private static final Path notesPath = Path.of("data/notes");
    private final NoteController noteController;
    private final AnchorPane notesContainer;

    public GenerateNotesRefMain(NoteController noteController, AnchorPane notesContainer) {
        this.noteController = noteController;
        this.notesContainer = notesContainer;
    }

    public void generateNote(boolean genFlag, String searchParam) throws IOException {

        ArrayList<NoteUnit> NoteInfoList;

        AnchorPane root = notesContainer;
        root.getChildren().clear();

        File[] files = notesPath.toFile().listFiles(file ->
                file.isFile() && file.getName().endsWith(".txt")
        );

        try{
            NoteInfoList = NoteInformation.ReadNoteInfo();
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        int createdFilesCounter = 0;

        if (files != null) {
            for (File fileUnit : files){
                String fileName = fileUnit.getName();
                String title = fileName.replace(".txt", "");

                // Flag to check, should it write info into noteInfo.txt
                boolean alreadyExistFlag = false;
                // Flag to check, should it render only favourite or all
                AtomicBoolean favouriteFlag = new AtomicBoolean(false);
                // Flag to check, should it render by searchBar
                boolean renderFlag;

                for (NoteUnit noteUnitInfo : NoteInfoList){
                    if (noteUnitInfo.getTitleNote().equals(title)){
                        alreadyExistFlag = true;
                        if (noteUnitInfo.getFavouriteFile()) favouriteFlag.set(true);
                        break;
                    }
                }

                if (!alreadyExistFlag){
                    NoteUnit noteToWrite = new NoteUnit(fileUnit.toPath(), " ");
                    try{
                        NoteInformation.AddToNoteInfo(noteToWrite);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (Objects.equals(searchParam, "")){
                    renderFlag = true;
                } else {
                    renderFlag = title.toLowerCase().contains(searchParam.toLowerCase());
                }

                if (renderFlag){
                    if (!genFlag){
                        renderNodes(createdFilesCounter, title, favouriteFlag, root, searchParam);
                        createdFilesCounter++;
                    } else{
                        if (favouriteFlag.get()){
                            renderNodes(createdFilesCounter, title, favouriteFlag, root, searchParam);
                            createdFilesCounter++;
                        }
                    }
                }
            }
        }
    }

    private void renderNodes(int index, String title, AtomicBoolean favouriteFlag, AnchorPane root, String searchParam){
        final int columns = 3;
        final int spacing = 500;

        Pane pane = new Pane();
        pane.setLayoutX(80 + (index % columns) * spacing);
        pane.setLayoutY(60 + (int) (index / columns) * spacing);
        pane.setPrefHeight(450);
        pane.setPrefWidth(450);

        Text noteTitle = new Text(title);
        noteTitle.setLayoutX(40);
        noteTitle.setLayoutY(60);
        noteTitle.getStyleClass().add("note-title");

        noteTitle.setWrappingWidth(370);


        pane.getChildren().add(noteTitle);
        pane.getStyleClass().add("note-body");

        Line noteLine = new Line(40, 80, 410, 80);
        noteLine.setStyle("-fx-opacity: 0.1;");
        pane.getChildren().add(noteLine);

        WebView noteContentPreview = new WebView();
        noteContentPreview.setLayoutX(40);
        noteContentPreview.setLayoutY(80);
        noteContentPreview.setPrefHeight(285);
        noteContentPreview.setPrefWidth(370);

        noteContentPreview.getEngine().setUserStyleSheetLocation("data:text/css, body { overflow: hidden !important; } html { overflow: hidden !important; }");

        Path notePath = Path.of("data/notes/" + title + ".txt");
        String contentToRender;
        try {
            contentToRender = MarkdownHandler.RenderMd(Files.readString(notePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        noteContentPreview.getEngine().loadContent(contentToRender);
        pane.getChildren().add(noteContentPreview);

        Button deleteNoteButton = new Button("×");
        Button updateNoteButton = new Button("Редактировать");
        Button favouriteButton = new Button("♥");

        deleteNoteButton.getStyleClass().add("delete-button");
        deleteNoteButton.setLayoutX(360);
        deleteNoteButton.setLayoutY(360);
        deleteNoteButton.setOnAction(event -> {
            noteController.DeleteNote(pane);
            try {
                generateNote(favouriteFlag.get(), searchParam);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        updateNoteButton.getStyleClass().add("update-button");
        updateNoteButton.setLayoutX(40);
        updateNoteButton.setLayoutY(360);
        updateNoteButton.setOnAction(event -> noteController.OpenExistedNote(pane));

        pane.getChildren().add(deleteNoteButton);
        pane.getChildren().add(updateNoteButton);

//        favouriteButton.setShape(new Circle(15));
//        favouriteButton.setMinSize(30, 30);
        favouriteButton.getStyleClass().add("favourite-button");
        favouriteButton.setLayoutX(390);
        favouriteButton.setLayoutY(30);
        favouriteButton.setStyle(favouriteFlag.get() ? "-fx-text-fill: red;" : "-fx-text-fill: grey;");
        favouriteButton.setOnAction(event -> {
            favouriteFlag.set(!favouriteFlag.get());
            favouriteButton.setStyle(favouriteFlag.get() ? "-fx-text-fill: red;" : "-fx-text-fill: grey;");

            try{
                NoteInformation.FavouriteNoteChange(title);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        pane.getChildren().add(favouriteButton);

        root.getChildren().add(pane);


    }
}