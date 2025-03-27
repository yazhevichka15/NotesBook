package org.notionclone.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class NoteFileManager {
    private static final Path notesPath = Paths.get("data/notes");

    public static Path createNoteFile() throws IOException {
        if (!Files.exists(notesPath)){
            Files.createDirectories(notesPath);
        }
        Path filePath = notesPath.resolve("Новая заметка" + ".txt");
        int countFileNew = 1;

        while (Files.exists(filePath)){
            filePath = notesPath.resolve("Новая заметка (" + countFileNew + ").txt");
            countFileNew++;
        }

        Files.createFile(filePath);
        return filePath;
    }
}
