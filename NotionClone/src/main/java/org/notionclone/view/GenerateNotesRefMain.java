package org.notionclone.view;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.notionclone.controller.NoteController;
import org.notionclone.model.NoteUnitInfo;
import org.notionclone.model.NoteUnits.NoteSimple;
import org.notionclone.model.NoteUnits.NoteUnit;
import org.notionclone.model.NoteInformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
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

    public void generateNote() throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        notesPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        renderNodes();

        Thread listenFolder = new Thread(() -> {
            try{
                while (true) {
                    WatchKey key = watchService.take();

                    boolean updated = false;
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == OVERFLOW) continue;

                        updated = true;
                    }

                    if (updated){
                        Platform.runLater(this::renderNodes);
                    }

                    boolean valid = key.reset();
                    if (!valid) break;
                }
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        });

        listenFolder.start();
    }

    private void renderNodes() {
        ArrayList<NoteUnitInfo> NoteInfoList;

        AnchorPane root = notesContainer;
        root.getChildren().clear();

        final int columns = 3;
        final int spacing = 500;

        File[] files = notesPath.toFile().listFiles(file ->
            file.isFile() && file.getName().endsWith(".txt")
        );

        try{
            NoteInfoList = NoteInformation.ReadNoteInfo();
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                boolean alreadyExistFlag = false;
                AtomicBoolean favouriteFlag = new AtomicBoolean(false);

                Pane pane = new Pane();

                pane.setLayoutX(80 + (i % columns) * spacing);
                pane.setLayoutY(60 + (int) (i / columns) * spacing);
                pane.setPrefHeight(450);
                pane.setPrefWidth(450);

                String fileName = files[i].getName();
                String title = fileName.replace(".txt", "");
                Text noteTitle = new Text(title);
                noteTitle.setLayoutX(40);
                noteTitle.setLayoutY(60);

                noteTitle.getStyleClass().add("note-title");

                for (NoteUnitInfo noteUnitInfo : NoteInfoList){
                    if (noteUnitInfo.getTitleNote().equals(title)){
                        alreadyExistFlag = true;
                        if (noteUnitInfo.getFavouriteFile()) favouriteFlag.set(true);
                        break;
                    }
                }

                if (!alreadyExistFlag){
                    NoteUnit noteToWrite = new NoteSimple(files[i].toPath(), " ");
                    try{
                        NoteInformation.AddToNoteInfo(noteToWrite);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                pane.getChildren().add(noteTitle);
                pane.getStyleClass().add("note-body");

                Line noteLine = new Line(40, 80, 410, 80);
                noteLine.setStyle("-fx-opacity: 0.1;");
                pane.getChildren().add(noteLine);

                Button deleteNoteButton = new Button("—");
                Button updateNoteButton = new Button("Редактировать");
                Button favouriteButton = new Button("");

                deleteNoteButton.getStyleClass().add("delete-button");
                deleteNoteButton.setLayoutX(360);
                deleteNoteButton.setLayoutY(360);
                deleteNoteButton.setOnAction(event -> noteController.DeleteNote(pane));

                updateNoteButton.getStyleClass().add("update-button");
                updateNoteButton.setLayoutX(40);
                updateNoteButton.setLayoutY(360);
                updateNoteButton.setOnAction(event -> noteController.OpenExistedNote(pane));

                pane.getChildren().add(deleteNoteButton);
                pane.getChildren().add(updateNoteButton);

                favouriteButton.setShape(new Circle(15));
                favouriteButton.setMinSize(30, 30);
                favouriteButton.setLayoutX(390);
                favouriteButton.setLayoutY(30);
                favouriteButton.setStyle(favouriteFlag.get() ? "-fx-background-color: red;" : "-fx-background-color: grey;");
                favouriteButton.setOnAction(event -> {
                    favouriteFlag.set(!favouriteFlag.get());
                    favouriteButton.setStyle(favouriteFlag.get() ? "-fx-background-color: red;" : "-fx-background-color: grey;");

                    try{
                        NoteInformation.FavouriteNoteChange(title);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });

                pane.getChildren().add(favouriteButton);

                root.getChildren().add(pane);
            }
        }
    }
}