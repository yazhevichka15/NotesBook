module org.notionclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens org.notionclone to javafx.fxml;
    exports org.notionclone;
    exports org.notionclone.controller;
    opens org.notionclone.controller to javafx.fxml;

    exports org.notionclone.model.NoteUnits to com.fasterxml.jackson.databind;
}