package org.notionclone.model;

public class NoteUnitInfo {
    private final String titleNote;
    private final String fileType;
    private final boolean favouriteFile;

    public NoteUnitInfo(String titleNote, String fileType, boolean favouriteFile){
        this.titleNote = titleNote;
        this.fileType = fileType;
        this.favouriteFile = favouriteFile;
    }

    public String getTitleNote() { return titleNote; }
    public String getFileType() { return fileType; }
    public boolean getFavouriteFile() { return favouriteFile; }
}