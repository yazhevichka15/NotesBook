package org.notionclone.model.NoteUnits;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class NoteUnit {
    private Path filePath;

    public NoteUnit(Path filePath){
        this.filePath = filePath;
    }

    public Path getFilePath() { return filePath; }
    public void setFilePath(Path filePath) { this.filePath = filePath; }
    public void saveFilePath(Path newFilePath) throws Exception{
        if (!Files.exists(filePath)){
            Files.createFile(newFilePath);
            return;
        }

        Files.move(filePath, newFilePath);
        this.filePath = newFilePath;
    }

    protected abstract void saveContent() throws IOException;
}
