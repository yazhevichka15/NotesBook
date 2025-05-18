package org.notionclone.model.NoteUnits;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "fileType",
//        visible = true
//)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = NoteSimple.class, name = "SimpleNote")
//})

public abstract class NoteUnit {
    private Path filePath;
    private boolean favouriteFile;
    private final String fileType;

    public NoteUnit(Path filePath, String fileType){
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public Path getFilePath() { return filePath; }
    public Boolean getFavouriteFile() { return favouriteFile; }
    public void setFavouriteFile(boolean flag) { this.favouriteFile = flag; }
    public String getFileType() { return fileType; }

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
