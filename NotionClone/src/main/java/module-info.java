module org.notionclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens org.notionclone to javafx.fxml;
    opens org.notionclone.controller to javafx.fxml;
    opens org.notionclone.model.NoteUnits to com.fasterxml.jackson.databind;

    exports org.notionclone;
    exports org.notionclone.controller;
}