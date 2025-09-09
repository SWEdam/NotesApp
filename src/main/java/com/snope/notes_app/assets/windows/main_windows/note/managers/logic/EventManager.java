package com.snope.notes_app.assets.windows.main_windows.note.managers.logic;

import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;
import com.snope.notes_app.assets.windows.main_windows.home.managers.logic.NoteManager;
import com.snope.notes_app.assets.windows.main_windows.note.NoteWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
   Warning: This class is an implementation for the Note class.
   - Do not use directly. Use Note's public methods instead.

 */

public class EventManager {

    private NoteWindow noteWindow;
    private HomeWindow home;
    private UndoRedoManager undoRedoManager;
    private FileManager fileManager;
    private NoteManager noteManager;
    private ImportExportManager importExportManager;

    public EventManager(NoteWindow noteWindow, HomeWindow home) {

        this.noteWindow = noteWindow;
        this.home = home;
        this.undoRedoManager = noteWindow.getUndoRedoManager();
        this.fileManager = noteWindow.getFileManager();
        this.noteManager = home.noteManager;
        this.importExportManager = new ImportExportManager(home);

    }

    public void setupListeners() {

        setupTitleListener();
        setupContentChangeListener();
        setupUndoRedoButtonListeners();
        setupHomeButtonListener();
        setupExportButtonListener();

    }

    private void setupTitleListener() {

        noteWindow.title.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                noteManager.renameNote(noteWindow.noteButton, noteWindow.title);
            }
        });

    }

    private void setupContentChangeListener() {

        noteWindow.textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleChange();
            }

            private void handleChange() {
                fileManager.handleContentChange();

                if (!undoRedoManager.shouldIgnoreChanges()) {

                    SwingUtilities.invokeLater(() -> {
                        undoRedoManager.pushState(
                                noteWindow.textArea.getText(),
                                noteWindow.textArea.getCaretPosition()
                        );
                    });

                }
            }
        });

    }

    private void setupUndoRedoButtonListeners() {

        noteWindow.undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                noteWindow.undoButton.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    undoRedoManager.undo();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                noteWindow.undoButton.setBackground(null);
            }
        });

        noteWindow.redoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                noteWindow.redoButton.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    undoRedoManager.redo();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                noteWindow.redoButton.setBackground(null);
            }
        });

    }

    private void setupHomeButtonListener() {

        noteWindow.homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                noteWindow.homeButton.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    noteWindow.navigateToHome(home, noteWindow.noteButton);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                noteWindow.homeButton.setBackground(null);
            }
        });

    }

    private void setupExportButtonListener() {

        noteWindow.exportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                importExportManager.export(noteWindow);
            }
        });

    }
}