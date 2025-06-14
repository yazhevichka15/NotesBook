module org.notionclone {
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.web;
    requires org.commonmark;
    requires org.commonmark.ext.gfm.tables;
    requires java.desktop;
//    requires org.commonmark.ext.gfm.strikethrough;

    opens org.notionclone to javafx.fxml;
    opens org.notionclone.controller to javafx.fxml;
    opens org.notionclone.model.NoteUnits to com.fasterxml.jackson.databind;

    exports org.notionclone;
    exports org.notionclone.controller;
}