package com.snope.notes_app.assets.windows.main_windows.note.managers.logic;

import com.snope.notes_app.assets.utils.FileUtils;
import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;
import com.snope.notes_app.assets.windows.main_windows.home.managers.logic.NoteManager;
import com.snope.notes_app.assets.windows.main_windows.note.NoteWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class ImportExportManager {

    private HomeWindow home;

    public ImportExportManager(HomeWindow home) {
        this.home = home;
    }

    public void selectAndImport() {

        JFileChooser fileSelector = new JFileChooser();
        NoteManager noteManager = new NoteManager(home);

        FileNameExtensionFilter txtExtension = new FileNameExtensionFilter("txt Files", "txt");
        FileNameExtensionFilter mdExtension = new FileNameExtensionFilter("md Files", "md");

        fileSelector.setFileFilter(txtExtension);
        fileSelector.addChoosableFileFilter(mdExtension);
        fileSelector.addChoosableFileFilter(mdExtension);
        fileSelector.setAcceptAllFileFilterUsed(false);

        int result = fileSelector.showOpenDialog(home);

        if (result != JFileChooser.APPROVE_OPTION) return;

        noteManager.createImportedNote(fileSelector.getSelectedFile());

    }

    public void export(NoteWindow noteWindow) {

        JFileChooser fileSelector = new JFileChooser();
        File defaultFile = new File(noteWindow.title.getText() + ".txt");

        FileNameExtensionFilter txtExtension = new FileNameExtensionFilter("txt Files", "txt");
        FileNameExtensionFilter mdExtension = new FileNameExtensionFilter("md Files", "md");

        fileSelector.setSelectedFile(defaultFile);
        fileSelector.setFileFilter(txtExtension);
        fileSelector.addChoosableFileFilter(mdExtension);
        fileSelector.addChoosableFileFilter(mdExtension);
        fileSelector.setAcceptAllFileFilterUsed(false);

        int result = fileSelector.showSaveDialog(home);

        if (result != JFileChooser.APPROVE_OPTION) return;

        try {
            FileUtils.overwriteFile(fileSelector.getSelectedFile(), noteWindow.textArea.getText());
        } catch (IOException e) {
            System.out.println("Couldnt write to file '" + fileSelector.getSelectedFile().toPath() + "'.");
        }

    }


}
