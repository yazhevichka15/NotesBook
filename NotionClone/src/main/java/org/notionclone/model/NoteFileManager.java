package org.notionclone.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class NoteFileManager {
    private static final Path notesPath = Path.of("data/notes");

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
