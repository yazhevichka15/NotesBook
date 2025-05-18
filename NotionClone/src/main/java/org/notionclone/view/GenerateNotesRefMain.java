package org.notionclone.view;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.notionclone.controller.NoteController;
import org.notionclone.model.JsonFileManager;
import org.notionclone.model.NoteUnits.NoteSimple;
import org.notionclone.model.NoteUnits.NoteUnit;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

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
        AnchorPane root = notesContainer;
        root.getChildren().clear();

        final int columns = 3;
        final int spacing = 500;

        File[] files = notesPath.toFile().listFiles(file ->
            file.isFile() && file.getName().endsWith(".txt")
        );

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
//                NoteUnit note = new NoteSimple(files[i].toPath(), " ");
//                try{
//                    JsonFileManager.AddToJsonFile(note);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

                Pane pane = new Pane();

                pane.setLayoutX(100 + (i % columns) * spacing);
                pane.setLayoutY(180 + (int) (i / columns) * spacing);
                pane.setPrefHeight(450);
                pane.setPrefWidth(450);

                String fileName = files[i].getName();
                String title = fileName.replace(".txt", "");
                Text noteTitle = new Text(title);
                noteTitle.setLayoutX(40);
                noteTitle.setLayoutY(60);

                noteTitle.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");

                pane.getChildren().add(noteTitle);
                pane.setStyle("-fx-background-radius: 25; -fx-background-color: white; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 20, 0.5, 0, 0);");

                Line noteLine = new Line(40, 80, 410, 80);
                noteLine.setStyle("-fx-opacity: 0.1;");
                pane.getChildren().add(noteLine);

                Button deleteNoteButton = new Button("—");
                Button updateNoteButton = new Button("Редактировать");

                deleteNoteButton.setStyle("-fx-pref-width: 50; -fx-pref-height: 50; -fx-background-color: #FF4D4D;" +
                        "-fx-background-radius: 10; -fx-font-size: 18;  -fx-text-fill: white; -fx-font-weight: bold;");
                deleteNoteButton.setLayoutX(360);
                deleteNoteButton.setLayoutY(360);
                deleteNoteButton.setOnAction(event -> noteController.DeleteNote(pane));

                updateNoteButton.setStyle("-fx-pref-width: 215; -fx-pref-height: 50; -fx-background-color: #02D3BB;" +
                        "-fx-background-radius: 10; -fx-font-size: 18;  -fx-text-fill: white; -fx-font-weight: bold;");
                updateNoteButton.setLayoutX(40);
                updateNoteButton.setLayoutY(360);
                updateNoteButton.setOnAction(event -> noteController.OpenExistedNote(pane));

                pane.getChildren().add(deleteNoteButton);
                pane.getChildren().add(updateNoteButton);

                Circle tagForFavorites = new Circle(395, 50, 15);
                tagForFavorites.setFill(Color.RED);

                pane.getChildren().add(tagForFavorites);

                root.getChildren().add(pane);
            }
        }
    }
}