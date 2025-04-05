package org.notionclone.view;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
        final int spacing = 300;

        AnchorPane root = (AnchorPane) scene.getRoot();
        root.setPrefWidth(1060);
        root.setPrefHeight(958);


        for (int i = 0; i < listOfNotes.size(); i++) {
            Pane pane = new Pane();

            pane.setLayoutX(450 + (i % columns) * spacing);
            pane.setLayoutY(180 + (int)(i / columns) * spacing);
            pane.setPrefHeight(230);
            pane.setPrefWidth(230);

            Text noteTitle = new Text("Заметка " + (i + 1));
            noteTitle.setLayoutX(10);
            noteTitle.setLayoutY(20);

            pane.getChildren().add(noteTitle);
            pane.setStyle("-fx-border-radius: 20px; -fx-background-color: white;");

            root.getChildren().add(pane);
        }
    }
}
