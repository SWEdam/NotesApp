package com.snope.notes_app.assets.windows.sub_windows;

import com.snope.notes_app.assets.App;
import com.snope.notes_app.assets.data_managers.Metadata.NoteMetadata;
import com.snope.notes_app.assets.data_managers.enums.SortOptions;
import com.snope.notes_app.assets.data_managers.enums.SortOrders;
import com.snope.notes_app.assets.objects.Note;
import com.snope.notes_app.assets.utils.AlgoUtils;
import com.snope.notes_app.assets.utils.JFrameUtils;
import com.snope.notes_app.assets.utils.VarUtils;
import com.snope.notes_app.assets.windows.main_windows.home.managers.logic.SortManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class RenamingWindow extends JFrame {

    public int DEFAULT_WIDTH = 400;
    public int DEFAULT_HEIGHT = 200;

    JTextField renameField = new JTextField("New Title");
    Note noteButton;
    JPanel notePanel;
    JLabel titleLabel;

    public RenamingWindow(Note noteButton, JPanel notePanel, JLabel titleLabel) {

        this.noteButton = noteButton;
        this.notePanel = notePanel;
        this.titleLabel = titleLabel;

        JFrameUtils.setupFrame(this, "Rename", DEFAULT_WIDTH, DEFAULT_HEIGHT, false);

        setupUI();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation(VarUtils.screenDimension.width/2 - this.getWidth()/2, VarUtils.screenDimension.height/2 - this.getHeight()/2);

        renameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    String newTitle = renameField.getText();

                    if (newTitle.trim().isEmpty()) {
                        System.out.println("Name can't be empty.");
                        return;
                    }
                    if (hasUnsupportedChar(newTitle)) {
                        System.out.println("The following characters '\\', '/', ':', '*', '?', '\"', '<', '>', '|' are not allowed.");
                        return;
                    }
                    if (App.metadataContainer.getNotes().containsKey(newTitle)) {
                        System.out.println("Title already exists.");
                        return;
                    }

                    try {

                        renameNote();
                        RenamingWindow.super.dispose();

                    } catch (IOException ex) {
                        System.out.println("The FilePath '" + App.PATH + "order.txt' doesn't exist.");
                    }

                }
                
            }
        });

        this.add(renameField);

        this.pack();
        this.setVisible(true);

    }

    private void setupUI() {

        renameField.setCaretColor(Color.LIGHT_GRAY);
        renameField.setForeground(Color.BLUE);
        renameField.setBackground(Color.BLACK);
        renameField.setFont(new Font("aFont", Font.BOLD, 30));
        renameField.setHorizontalAlignment(SwingConstants.CENTER);
        renameField.setBorder(null);
        renameField.setSize(this.getSize());

    }

    private void renameNote() throws IOException {

        String oldTitle = noteButton.getTitle();
        String newTitle = renameField.getText();

        boolean wasRenamed = new File(App.PATH + "notes/" + oldTitle + ".txt")
                .renameTo(new File(App.PATH + "notes/" + newTitle + ".txt"));
        if (!wasRenamed) {
            System.out.println("Failed to rename '" + App.PATH + "notes/" + oldTitle + ".txt'");
            return;
        }

        Map<String, NoteMetadata> newNotes = new LinkedHashMap<>();
        for (Map.Entry<String, NoteMetadata> entry : App.metadataContainer.getNotes().entrySet()) {
            if (entry.getKey().equals(oldTitle)) {
                entry.getValue().setNoteTitle(newTitle);
                newNotes.put(newTitle, entry.getValue());
            } else newNotes.put(entry.getKey(), entry.getValue());
        }
        App.metadataContainer.setNotes(newNotes);

        if (SortManager.getSortOption() == SortOptions.LEXICOGRAPHICALLY) {
            try {
                SortManager.sortAndUpdateUI();
            } catch (IOException e) {
                System.out.println("Failed to sort and update UI: " + e.getMessage());
            }
        } else {
            App.mapper.writeValue(App.metadataFile, App.metadataContainer);
            App.metadataNode = App.mapper.readTree(App.metadataFile);
        }

        noteButton.setTitle(newTitle);
        if (titleLabel != null) titleLabel.setText(newTitle);

    }

    private boolean hasUnsupportedChar(String str) {

        Set<Character> unsupportedChars = new HashSet<>(Set.of(
                '\\', '/', ':', '*', '?', '\"', '<', '>', '|'
        ));

        return AlgoUtils.containsCharFromSet(str, unsupportedChars);

    }

}
