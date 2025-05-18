package org.notionclone.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.notionclone.model.NoteUnits.NoteSimple;
import org.notionclone.model.NoteUnits.NoteUnit;

public class JsonFileManager {
    private static final Path jsonDirPath = Path.of("data/require");
    private static final File jsonFile = Path.of("data/require/noteInfo.json").toFile();

    public static void AddToJsonFile(NoteUnit noteToAdd) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        if (!Files.exists(jsonDirPath)){
            Files.createDirectories(jsonDirPath);
        }

        List<NoteUnit> noteList;

        System.out.println("Перед");
        if (jsonFile.exists()){
            noteList = mapper.readValue(jsonFile, new TypeReference<List<NoteUnit>>() {});
        } else {
            noteList = new ArrayList<>();
        }
        System.out.println("После");
        noteList.add(noteToAdd);

        mapper.writeValue(jsonFile, noteList);
    }
}
