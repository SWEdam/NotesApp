package com.snope.notes_app.assets.windows.main_windows.note.managers.logic;

import com.snope.notes_app.assets.App;
import com.snope.notes_app.assets.objects.Note;
import com.snope.notes_app.assets.utils.FileUtils;
import com.snope.notes_app.assets.windows.main_windows.note.NoteWindow;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
   Warning: This class is an implementation for the Note class.
   - Do not use directly. Use Note's public methods instead.

 */

public class FileManager {

    private NoteWindow note;
    private Timer autoSaveTimer;
    private boolean hasUnsavedChanges = false;

    private static final int AUTO_SAVE_DELAY = 750;

    public FileManager(NoteWindow note) {

        this.note = note;
        setupAutoSave();

    }

    public void createNewNoteFile(Note noteButton) {

        note.noteFile = new File(App.PATH + "notes/" + noteButton.getTitle() + ".txt");

        if (!note.noteFile.exists()) {
            try {
                note.noteFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Couldn't create file '" + note.noteFile.getPath() + "'");
            }
        }

    }

    public static void createImportedFile(File importedFile) throws IOException {

        File file = new File(App.PATH + "notes/" + importedFile.getName().substring(0, importedFile.getName().lastIndexOf('.')) + ".txt");
        FileUtils.overwriteFile(file, FileUtils.readFileAsStr(importedFile));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Couldn't create file '" + file.getPath() + "'");
            }
        }

    }

    public void loadNote(Note noteButton) {

        note.noteFile = new File(App.PATH + "notes/" + noteButton.getTitle() + ".txt");

        try {
            note.title.setText(noteButton.getTitle());
            note.textArea.setText(Files.readString(note.noteFile.toPath()).trim());
        } catch (IOException e) {
            System.out.println("The file '" + note.noteFile.toPath() + "' does not exist.");
        }

    }

    public void save() {

        try {
            Files.writeString(note.noteFile.toPath(), note.textArea.getText());
        } catch (IOException e) {
            System.out.println("The file '" + note.noteFile.toPath() + "' does not exist.");
        }

    }

    void handleContentChange() {

        hasUnsavedChanges = true;
        if (autoSaveTimer.isRunning()) autoSaveTimer.restart();
        else autoSaveTimer.start();

    }

    private void setupAutoSave() {

        autoSaveTimer = new Timer(AUTO_SAVE_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasUnsavedChanges) {
                    save();
                    hasUnsavedChanges = false;
                }
                autoSaveTimer.stop();
            }
        });
        autoSaveTimer.setRepeats(false);

    }

}