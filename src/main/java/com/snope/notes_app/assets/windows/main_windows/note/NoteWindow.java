package com.snope.notes_app.assets.windows.main_windows.note;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snope.notes_app.assets.App;
import com.snope.notes_app.assets.data_managers.enums.SortOptions;
import com.snope.notes_app.assets.data_managers.enums.SortOrders;
import com.snope.notes_app.assets.objects.Note;
import com.snope.notes_app.assets.utils.JFrameUtils;
import com.snope.notes_app.assets.utils.VarUtils;
import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;
import com.snope.notes_app.assets.windows.main_windows.home.managers.logic.SortManager;
import com.snope.notes_app.assets.windows.main_windows.note.managers.logic.EventManager;
import com.snope.notes_app.assets.windows.main_windows.note.managers.logic.FileManager;
import com.snope.notes_app.assets.windows.main_windows.note.managers.logic.ShortcutManager;
import com.snope.notes_app.assets.windows.main_windows.note.managers.logic.UndoRedoManager;
import com.snope.notes_app.assets.windows.main_windows.note.managers.ui.UIManager;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class NoteWindow extends JFrame {

    public JTextArea textArea = new JTextArea();
    public JScrollPane scrollPane = new JScrollPane(textArea, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
    public JButton homeButton = new JButton("home");
    public JLabel title = new JLabel();
    public JButton undoButton = new JButton("<");
    public JButton redoButton = new JButton(">");
    public Note noteButton;

    private UIManager uiManager;
    private FileManager fileManager;
    private UndoRedoManager undoRedoManager;
    private ShortcutManager shortcutManager;
    private EventManager eventManager;

    public File noteFile;

    public static final int DEFAULT_WIDTH = (int) (VarUtils.screenDimension.width/1.5);
    public static final int DEFAULT_HEIGHT = (int) (VarUtils.screenDimension.height/1.5);

    public NoteWindow(HomeWindow home, boolean isNew, Note noteButton) {

        this.noteButton = noteButton;

        fileManager = new FileManager(this);
        uiManager = new UIManager(this);
        undoRedoManager = new UndoRedoManager(this);
        eventManager = new EventManager(this, home);
        shortcutManager = new ShortcutManager(this);

        if (isNew) {
            fileManager.createNewNoteFile(noteButton);
            title.setText(noteButton.getTitle());
        }
        else fileManager.loadNote(noteButton);

        JFrameUtils.setupFrame(this, "Notes", DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        uiManager.setupUI();
        eventManager.setupListeners();
        shortcutManager.setupShortcuts();

        setupWindowListeners(home);
        JFrameUtils.addComponentsTo(this, scrollPane, homeButton, title, undoButton, redoButton);

        this.setVisible(true);
        home.setVisible(false);
        textArea.requestFocusInWindow();

    }

    public void navigateToHome(HomeWindow home, Note noteButton) {

        try {

            String date = LocalDateTime.now().format(App.dateTimeFormatter);

            ObjectNode newJson = (ObjectNode) App.metadataNode;
            ((ObjectNode) newJson.get("notes").get(noteButton.getTitle()))
                    .put("date_modified", date);
            App.mapper.writeValue(App.metadataFile, newJson);
            App.metadataContainer.getNote(title.getText()).setDate_modified(date);

            noteButton.setDateModified(date);

            if (SortManager.getSortOption() == SortOptions.DATE_MODIFIED) {
                SortManager.sortAndUpdateUI();
            }

            fileManager.save();
            switchToHomeTab(this, home);

        } catch (IOException e) {
            System.out.println("Failed to edit '" + App.metadataFile + "'.");
        }

    }

    public static void switchToHomeTab(NoteWindow note, HomeWindow home) {
        note.dispose();
        home.setVisible(true);
    }

    private void setupWindowListeners(HomeWindow home) {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                home.setVisible(true);
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                uiManager.handleResize();
            }
        });
    }


    public UndoRedoManager getUndoRedoManager() {
        return this.undoRedoManager;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

}