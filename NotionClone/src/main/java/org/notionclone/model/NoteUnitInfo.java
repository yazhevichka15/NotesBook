package org.notionclone.model;

public class NoteUnitInfo {
    private final String titleNote;
    private final boolean favouriteFile;

    public NoteUnitInfo(String titleNote, boolean favouriteFile){
        this.titleNote = titleNote;
        this.favouriteFile = favouriteFile;
    }

    public String getTitleNote() { return titleNote; }
    public boolean getFavouriteFile() { return favouriteFile; }
}