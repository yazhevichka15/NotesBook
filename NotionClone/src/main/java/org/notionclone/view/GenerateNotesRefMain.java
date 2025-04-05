package org.notionclone.view;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class GenerateNotesRefMain {
    private static final Path notesPath = Path.of("data/notes");
    protected static ArrayList<File> listOfNotes;

    public GenerateNotesRefMain(){
        File[] fileList = notesPath.toFile().listFiles();
        if (fileList != null){
            listOfNotes = new ArrayList<>(fileList.length);
            listOfNotes.addAll(Arrays.asList(fileList));
        } else{
            listOfNotes = new ArrayList<>();
        }
    }

    public void generateNote(Scene scene) {
        final int columns = 3;
        final int spacing = 250;

        AnchorPane root = (AnchorPane) scene.getRoot();

        root.setStyle("-fx-background-color: blue;");

        for (int i = 0; i < listOfNotes.size(); i++) {
            Pane pane = new Pane();

            pane.setLayoutX((i % columns) * spacing);
            pane.setLayoutY((int)(i / columns) * spacing);
            pane.setPrefHeight(230);
            pane.setPrefWidth(230);
            pane.setStyle("-fx-border-radius: 20; -fx-background-color: red;");

            root.getChildren().add(pane);
        }
    }
}
