package org.notionclone.model.NoteUnits;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NoteSimple extends NoteUnit{
    private String content;

    public NoteSimple(Path filePath, String content) {
        super(filePath);
        this.content = content;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override
    public void saveContent() throws IOException{
        Files.writeString(getFilePath(), content);
    }
}
