package com.snope.notes_app.assets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.snope.notes_app.assets.data_managers.Metadata.MetadataContainer;
import com.snope.notes_app.assets.data_managers.Metadata.NoteMetadata;
import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snope.notes_app.assets.data_managers.Settings;
import com.snope.notes_app.assets.windows.main_windows.home.managers.ui.NoteUIManager;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class App {

    public static final String PATH = "src/data/";

    public static File settingsFile = new File(PATH + "settings.json");
    public static File metadataFile = new File(PATH + "meta-data.json");

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new JavaTimeModule()
                    .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter))
                    .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter))
            );

    public static Settings settings = new Settings();
    public static JsonNode settingsNode;

    public static MetadataContainer metadataContainer = new MetadataContainer();
    public static JsonNode metadataNode;


    public static void main(String[] args) throws IOException {

        // Recreate main data folder if missing.
        new File(PATH).mkdir();

        // Make sure the notes directory exists before loading Count and Order data, recreate if missing.
        new File(PATH + "notes/").mkdir();
        loadSettings();
        loadMetadata();

        // Initialize the App.
        HomeWindow home = new HomeWindow();

        // Load the Note Entries.
        if (!metadataContainer.getNotes().isEmpty()) {

            HomeWindow.hideWelcomeText();
            for (Map.Entry<String, NoteMetadata> entry : metadataContainer.getNotes().entrySet()) {
                NoteUIManager.createNoteEntry(
                        home,
                        entry.getKey(),
                        entry.getValue().getDate_created(),
                        entry.getValue().getDate_modified()
                );
            }
            home.notePanel.revalidate();
            home.notePanel.repaint();

        }

    }

    public static void loadSettings() throws IOException {

        if (!settingsFile.exists()) mapper.writeValue(settingsFile, settings);
        else settings = mapper.readValue(settingsFile, Settings.class);

        settingsNode = mapper.readTree(settingsFile);

    }

    public static void loadMetadata() throws IOException {

        if (!metadataFile.exists()) mapper.writeValue(metadataFile, metadataContainer);
        else metadataContainer = mapper.readValue(metadataFile, MetadataContainer.class);

        metadataNode = mapper.readTree(metadataFile);

    }


}
