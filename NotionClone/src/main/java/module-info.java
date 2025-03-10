module org.notionclone {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.notionclone to javafx.fxml;
    exports org.notionclone;
}