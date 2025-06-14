package org.notionclone.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import org.notionclone.model.NoteUnits.NoteUnit;

public class NoteInformation {
    private static final Path noteDir = Path.of("data/notes");
    private static final Path infDir = Path.of("data/require");
    private static final File infPath = Path.of("data/require/noteInfo.txt").toFile();

    public static ArrayList<NoteUnit> ReadNoteInfo() throws IOException {
        ArrayList<NoteUnit> noteUnitInfoList = new ArrayList<>();
        File[] filesInDir = noteDir.toFile().listFiles();

        ArrayList<String> validLines = new ArrayList<>();

        if (!Files.exists(infDir)){
            Files.createDirectories(infDir);
        }
        if (!infPath.exists()){
            Files.createFile(infPath.toPath());
        }

        Scanner scanner = new Scanner(infPath);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine().trim();

            if (!line.startsWith("{") || !line.endsWith("}")){
                continue;
            }

            String originalLine = line;
            line = line.substring(1, line.length() - 1);
            String[] parts = line.split(",");

            if (parts.length == 2){
                String title = parts[0].trim();
                boolean favouriteFlag = Boolean.parseBoolean(parts[1].trim());

                boolean flag = false;
                for (File file : filesInDir){
                    if (file.getName().equals(title)) {
                        flag = true;

                        NoteUnit noteUniteToAdd = new NoteUnit(title.replace(".txt", ""), favouriteFlag);
                        noteUnitInfoList.add(noteUniteToAdd);
                        
                        break;
                    }
                }

                if (flag){
                    validLines.add(originalLine + "\n");
                }
            }
        }

        scanner.close();

        WriterToFile(String.join("", validLines), false);

        return noteUnitInfoList;
    }

    public static void FavouriteNoteChange(String titleToChange) throws IOException {
        ArrayList<String> validLines = new ArrayList<>();

        if (!Files.exists(infDir)){
            Files.createDirectories(infDir);
        }
        if (!infPath.exists()){
            Files.createFile(infPath.toPath());
        }

        Scanner scanner = new Scanner(infPath);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (!line.startsWith("{") || !line.endsWith("}")) {
                continue;
            }

            String originalLine = line;
            line = line.substring(1, line.length() - 1);
            String[] parts = line.split(",");

            if (parts.length == 2) {
                String title = parts[0].trim();

                if (title.equals(titleToChange + ".txt")) {
                    boolean favouriteFlag = Boolean.parseBoolean(parts[1].trim());

                    favouriteFlag = !favouriteFlag;

                    String noteStringToAdd = "{ " + title + ", " + favouriteFlag + " }";
                    validLines.add(noteStringToAdd + "\n");
                } else {
                    validLines.add(originalLine + "\n");
                }
            }
        }

        scanner.close();

        WriterToFile(String.join("", validLines), false);
    }

    public static void AddToNoteInfo(NoteUnit noteToAdd) throws IOException {
        if (!Files.exists(infDir)){
            Files.createDirectories(infDir);
        }

        String fileName = noteToAdd.getFilePath().toFile().getName();
        String stringToWrite = "{ " + fileName + ", "  + noteToAdd.getFavouriteFile() + " }" + "\n";

        WriterToFile(stringToWrite, true);
    }

    private static void WriterToFile(String stringToWrite, boolean appendMode) {
        try(FileWriter writer = new FileWriter(infPath, appendMode)){
            writer.write(stringToWrite);

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
