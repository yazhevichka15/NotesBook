package org.notionclone.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.notionclone.model.NoteUnits.NoteUnit;

public class JsonFileManager {
    private static final Path jsonDirPath = Path.of("data/require");
    private static final File jsonFile = Path.of("data/require/noteInfo.json").toFile();

    public static void AddToJsonFile(NoteUnit noteToAdd) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, false);

        if (!Files.exists(jsonDirPath)){
            Files.createDirectories(jsonDirPath);
        }

        mapper.writeValue(jsonFile, noteToAdd);
    }
}
