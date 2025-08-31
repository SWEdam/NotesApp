package com.snope.notes_app.assets.windows.main_windows.note.managers.logic;

import com.snope.notes_app.assets.objects.TextState;
import com.snope.notes_app.assets.windows.main_windows.note.NoteWindow;

import java.awt.Color;
import java.util.Stack;

/*
   Warning: This class is an implementation for the Note class.
   - Do not use directly. Use Note's public methods instead.

 */

public class UndoRedoManager {

    private NoteWindow note;
    private Stack<TextState> undoStack = new Stack<>();
    private Stack<TextState> redoStack = new Stack<>();

    private boolean ignoreChanges = false;
    private long lastChangeTime = 0;

    private static final int GROUPING_DELAY = 500;

    public UndoRedoManager(NoteWindow note) {

        this.note = note;
        resetStacks();

    }

    void undo() {

        if (undoStack.size() <= 1) return;

        redoStack.push(undoStack.pop());

        ignoreChanges = true;
        setTextState(undoStack.peek());
        ignoreChanges = false;

        updateButtonStates();

    }

    void redo() {

        if (redoStack.empty()) return;

        ignoreChanges = true;
        setTextState(undoStack.push(redoStack.pop()));
        ignoreChanges = false;

        updateButtonStates();

    }

    void pushState(String text, int caretPos) {

        long currentTime = System.currentTimeMillis();
        boolean shouldGroup = (currentTime - lastChangeTime) < GROUPING_DELAY;

        TextState currentState = new TextState(text, caretPos);

        if (shouldGroup && undoStack.size() > 1) {
            undoStack.pop();
            undoStack.push(currentState);
        } else undoStack.push(currentState);

        redoStack = new Stack<>();
        lastChangeTime = currentTime;

        updateButtonStates();

    }

    boolean shouldIgnoreChanges() {
        return ignoreChanges;
    }

    void setIgnoreChanges(boolean ignore) {
        this.ignoreChanges = ignore;
    }

    private void resetStacks() {

        undoStack = new Stack<>();
        redoStack = new Stack<>();

        undoStack.push(new TextState(note.textArea.getText(), note.textArea.getCaretPosition()));

    }

    private void setTextState(TextState textState) {

        note.textArea.setText(textState.text());

        try {
            note.textArea.setCaretPosition(textState.caretPos());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid caret position in TextState");
        }

    }

    private void updateButtonStates() {

        if (undoStack.size() > 1) {
            note.undoButton.setContentAreaFilled(true);
            note.undoButton.setForeground(Color.LIGHT_GRAY);
        } else {
            note.undoButton.setContentAreaFilled(false);
            note.undoButton.setForeground(Color.DARK_GRAY);
        }

        if (!redoStack.empty()) {
            note.redoButton.setContentAreaFilled(true);
            note.redoButton.setForeground(Color.LIGHT_GRAY);
        } else {
            note.redoButton.setContentAreaFilled(false);
            note.redoButton.setForeground(Color.DARK_GRAY);
        }

    }

}
