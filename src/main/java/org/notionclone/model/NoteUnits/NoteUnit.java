package org.notionclone.model.NoteUnits;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NoteUnit {
    private String titleNote;
    private String contentNote;
    private Path filePath;
    private boolean favouriteFile;

    public NoteUnit(Path filePath, String content){
        this.filePath = filePath;
        this.contentNote = content;
    }

    public NoteUnit(String titleNote, boolean favouriteFile){
        this.titleNote = titleNote;
        this.favouriteFile = favouriteFile;
    }

    public String getTitleNote() { return titleNote; }
    public String getContent() { return contentNote; }
    public Path getFilePath() { return filePath; }
    public Boolean getFavouriteFile() { return favouriteFile; }

    public void setContent(String content) { this.contentNote = content; }

    public void saveFilePath(Path newFilePath) throws Exception{
        if (!Files.exists(filePath)){
            Files.createFile(newFilePath);
            return;
        }
        Files.move(filePath, newFilePath);
        this.filePath = newFilePath;
    }

    public void saveContent() throws IOException {
        Files.writeString(getFilePath(), contentNote);
    }
}
