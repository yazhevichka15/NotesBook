module org.notionclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens org.notionclone to javafx.fxml;
    exports org.notionclone;
    exports org.notionclone.controller;
    opens org.notionclone.controller to javafx.fxml;
}