package com.snope.notes_app.assets.windows.main_windows.note.managers.ui;

import com.snope.notes_app.assets.windows.main_windows.note.NoteWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/*
   Warning: This class is an implementation for the Note class.
   - Do not use directly. Use Note's public methods instead.

 */

public class UIManager {

    private NoteWindow note;

    public UIManager(NoteWindow note) { this.note = note; }

    public void setupUI() {

        setupTextArea();
        setupScrollPane();
        setupHomeButton();
        setupTitle();
        setupUndoRedoButtons();

    }

    public void handleResize() {



    }

    private void setupTextArea() {

        note.textArea.setMargin(new Insets(10, 10, 10, 10));
        note.textArea.setLineWrap(true);
        note.textArea.setWrapStyleWord(true);
        note.textArea.setCaretColor(Color.LIGHT_GRAY);
        note.textArea.setSelectedTextColor(Color.LIGHT_GRAY);
        note.textArea.setSelectionColor(Color.BLUE);

    }

    private void setupScrollPane() {

        note.scrollPane.getViewport().getView().setBackground(Color.BLACK);
        note.scrollPane.getViewport().getView().setForeground(Color.WHITE);
        note.scrollPane.getViewport().getView().setFont(new Font("aFont", Font.BOLD, 20));
        note.scrollPane.setSize((int) (note.getWidth()/1.2), (int) (note.getHeight()/1.4));
        note.scrollPane.setLocation(note.getWidth()/2 - note.scrollPane.getWidth()/2,
                note.getHeight()/2 - note.scrollPane.getHeight()/2);
        note.scrollPane.getVerticalScrollBar().setBackground(Color.DARK_GRAY);

    }

    private void setupHomeButton() {

        note.homeButton.setFocusable(false);
        note.homeButton.setBorder(new LineBorder(Color.BLUE, 3));
        note.homeButton.setForeground(Color.BLUE);
        note.homeButton.setSize(70, 70);
        note.homeButton.setLocation(0, 1);
        note.homeButton.setBackground(null);
        note.homeButton.setFont(new Font("aFont", Font.BOLD, 20));

    }

    private void setupTitle() {

        note.title.setForeground(Color.LIGHT_GRAY);
        note.title.setBackground(Color.BLACK);
        note.title.setFont(new Font("aFont", Font.BOLD, 30));
        note.title.setSize(380, 70);
        note.title.setLocation(note.getWidth()/2 - note.title.getWidth()/2, 5);
        note.title.setHorizontalAlignment(SwingConstants.CENTER);
        note.title.setBorder(null);

    }

    private void setupUndoRedoButtons() {

        note.undoButton.setFocusable(false);
        note.undoButton.setBorder(new LineBorder(Color.BLUE, 2));
        note.undoButton.setSize(30, 30);
        note.undoButton.setLocation(note.homeButton.getWidth() + 50,
                note.homeButton.getWidth()/2 - note.undoButton.getHeight()/2);
        note.undoButton.setBackground(null);
        note.undoButton.setFont(new Font("aFont", Font.BOLD, 20));
        note.undoButton.setContentAreaFilled(false);

        note.redoButton.setFocusable(false);
        note.redoButton.setBorder(new LineBorder(Color.BLUE, 2));
        note.redoButton.setSize(30, 30);
        note.redoButton.setLocation(note.undoButton.getX() + note.undoButton.getWidth(),
                note.undoButton.getY());
        note.redoButton.setBackground(null);
        note.redoButton.setFont(new Font("aFont", Font.BOLD, 20));
        note.redoButton.setContentAreaFilled(false);

    }

}