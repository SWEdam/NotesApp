package com.snope.notes_app.assets.windows.main_windows.home.managers.logic;

import com.snope.notes_app.assets.App;
import com.snope.notes_app.assets.data_managers.Metadata.NoteMetadata;
import com.snope.notes_app.assets.data_managers.enums.SortOrders;
import com.snope.notes_app.assets.objects.Note;
import com.snope.notes_app.assets.utils.FileUtils;
import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;
import com.snope.notes_app.assets.windows.main_windows.home.managers.ui.NoteUIManager;
import com.snope.notes_app.assets.windows.main_windows.note.NoteWindow;
import com.snope.notes_app.assets.windows.main_windows.note.managers.logic.FileManager;
import com.snope.notes_app.assets.windows.sub_windows.RenamingWindow;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;

/*
   Warning: This class is an implementation for the Home class.
   - Do not use directly. Use Home's public methods instead.

 */

public class NoteManager {

    private HomeWindow home;

    public NoteManager(HomeWindow home) {
        this.home = home;
    }

    public void createNewNote() {

        Note noteButton = registerNewNote(null);

        switchToNoteTab(true, noteButton);

    }

    public void createImportedNote(File importedFile) {

        if (App.metadataContainer.getNotes().containsKey(importedFile.getName().substring(0, importedFile.getName().lastIndexOf('.')))) {
            System.out.println("The selected file already exists.");
            return;
        }

        Note noteButton = registerNewNote(importedFile);

        switchToNoteTab(false, noteButton);

    }

    public void openNote(Note noteButton) {
        switchToNoteTab(false, noteButton);
    }

    public void deleteNote(Note noteButton) {

        try {

            App.metadataContainer.getNotes().remove(noteButton.getTitle());
            App.metadataContainer.setCount(App.metadataContainer.getCount() - 1);
            App.mapper.writeValue(App.metadataFile, App.metadataContainer);
            App.metadataNode = App.mapper.readTree(App.metadataFile);

            home.notePanel.remove(noteButton);
            home.notePanel.updateUI();

            if (App.metadataContainer.getNotes().isEmpty()) HomeWindow.showWelcomeText();

            deleteNoteFile(noteButton.getTitle());

        } catch (IOException e) {
            System.out.println("Couldn't edit '" + App.metadataFile + "'.");
        }

    }

    public void renameNote(Note noteButton, JLabel title) {
        new RenamingWindow(noteButton, home.notePanel, title);
    }

    private void switchToNoteTab(boolean isNew, Note noteButton) {
        HomeWindow.hideWelcomeText();
        new NoteWindow(home, isNew, noteButton);
    }

    private Note registerNewNote(File importedFile) {

        try {

            String date = LocalDateTime.now().format(App.dateTimeFormatter);
            String title;

            if (importedFile == null) {

                // Reinforced to make sure there's no duplicates.
                int incrementer = 1;
                do {
                    title = "Note " + (App.metadataContainer.getCount() + incrementer);
                    incrementer++;
                } while(App.metadataContainer.getNotes().containsKey(title));

            } else {
                title = importedFile.getName().substring(0, importedFile.getName().lastIndexOf('.'));
                FileManager.createImportedFile(importedFile);
            }

            App.metadataContainer.addNote(title, new NoteMetadata(date));
            App.metadataContainer.setCount(App.metadataContainer.getCount() + 1);

            SortManager.sortAndUpdateUI();

            for (Component component : home.notePanel.getComponents()) {
                if (component instanceof Note note && note.getTitle().equals(title)) {
                    return note;
                }
            }

            throw new RuntimeException("Failed to find newly created note button.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to register new note.");
        }

    }

    private void deleteNoteFile(String noteText) {

        new File(App.PATH + "notes/" + noteText + ".txt").delete();

    }

}