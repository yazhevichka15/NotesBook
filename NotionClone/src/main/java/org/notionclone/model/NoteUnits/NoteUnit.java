package org.notionclone.model.NoteUnits;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NoteUnit {
    private Path filePath;
    private boolean favouriteFile;
    private String content;

    public NoteUnit(Path filePath, String content){
        this.filePath = filePath;
        this.content = content;
    }

    public Path getFilePath() { return filePath; }
    public Boolean getFavouriteFile() { return favouriteFile; }
    public String getContent() { return content; }

    public void setFavouriteFile(boolean flag) { this.favouriteFile = flag; }
    public void setContent(String content) { this.content = content; }

    public void saveFilePath(Path newFilePath) throws Exception{
        if (!Files.exists(filePath)){
            Files.createFile(newFilePath);
            return;
        }

        Files.move(filePath, newFilePath);
        this.filePath = newFilePath;
    }

    public void saveContent() throws IOException {
        Files.writeString(getFilePath(), content);
    }
}
