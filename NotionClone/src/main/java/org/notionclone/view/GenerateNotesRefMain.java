package org.notionclone.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.notionclone.model.NoteUnits.NoteSimple;
import org.notionclone.model.NoteUnits.NoteUnit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class GenerateNotesRefMain {
    private static final Path notesPath = Path.of("data/notes");
    protected static ArrayList<NoteUnit> listOfNotes;

    public GenerateNotesRefMain(){
        File[] fileList = notesPath.toFile().listFiles();
        if (fileList != null){
            listOfNotes = new ArrayList<>(fileList.length);
            for (File file : fileList) {
                listOfNotes.add(new NoteSimple(Path.of(String.valueOf(file)), " "));
            }
        } else{
            listOfNotes = new ArrayList<>();
        }
    }

    public void generateNote(Scene scene) throws IOException {
        final int columns = 3;
        final int spacing = 500;

        AnchorPane root = (AnchorPane) scene.getRoot();
        root.setPrefWidth(1060);
        root.setPrefHeight(958);


        for (int i = 0; i < listOfNotes.size(); i++) {
            Pane pane = new Pane();

            pane.setLayoutX(400 + (i % columns) * spacing);
            pane.setLayoutY(180 + (int)(i / columns) * spacing);
            pane.setPrefHeight(450);
            pane.setPrefWidth(450);

            Text noteTitle = new Text(String.valueOf(listOfNotes.get(i)));
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

            updateNoteButton.setStyle("-fx-pref-width: 215; -fx-pref-height: 50; -fx-background-color: #02D3BB;" +
                    "-fx-background-radius: 10; -fx-font-size: 18;  -fx-text-fill: white; -fx-font-weight: bold;");
            updateNoteButton.setLayoutX(40);
            updateNoteButton.setLayoutY(360);

            pane.getChildren().add(deleteNoteButton);
            pane.getChildren().add(updateNoteButton);

            Circle tagForFavorites = new Circle(395, 50, 15);
            tagForFavorites.setFill(Color.RED);

            pane.getChildren().add(tagForFavorites);

            root.getChildren().add(pane);
        }
    }
}
