package com.snope.notes_app.assets.windows.main_windows.note.managers.logic;

import com.snope.notes_app.assets.windows.main_windows.note.NoteWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/*
   Warning: This class is an implementation for the Note class.
   - Do not use directly. Use Note's public methods instead.

 */

public class ShortcutManager {

    private NoteWindow note;
    private UndoRedoManager undoRedoManager;

    public ShortcutManager(NoteWindow note) {

        this.note = note;
        this.undoRedoManager = note.getUndoRedoManager();

    }

    public void setupShortcuts() {

        setupUndoShortcut();
        setupRedoShortcut();

    }

    private void setupUndoShortcut() {

        note.textArea.getInputMap(JComponent.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "undo");

        note.textArea.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoRedoManager.undo();
            }
        });

    }

    private void setupRedoShortcut() {

        note.textArea.getInputMap(JComponent.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), "redo");

        note.textArea.getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoRedoManager.redo();
            }
        });

    }

}