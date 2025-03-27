package org.notionclone.model.NoteUnits;

import java.io.IOException;
import java.nio.file.Path;

public abstract class NoteUnit {
    private String title;
    private Path filePath;

    public NoteUnit(String title, Path filePath){
        this.title = title;
        this.filePath = filePath;
    }

    protected abstract void saveContent() throws IOException;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
}
