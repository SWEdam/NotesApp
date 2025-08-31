package com.snope.notes_app.assets.windows.main_windows.home.managers.ui;

import com.snope.notes_app.assets.objects.Note;
import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;
import com.snope.notes_app.assets.windows.main_windows.home.managers.logic.ActionMenuManager;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
   Warning: This class is an implementation for the Home class.
   - Do not use directly. Use Home's public methods instead.

 */

public class NoteUIManager {

    public static Note createNoteEntry(HomeWindow home, String title, String dateCreated, String dateModified) {

        Note noteButton = new Note(title, 0, dateCreated, dateModified);
        noteButton.setText(title);

        noteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Left click
                    home.openNote(noteButton);
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Right click
                    ActionMenuManager.createActionMenu(noteButton, home)
                            .show(noteButton, 10, 10);
                }
            }
        });

        noteButton.setFocusable(false);
        noteButton.setForeground(Color.BLUE);
        noteButton.setBackground(null);
        noteButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        noteButton.setPreferredSize(new Dimension(
                home.notePanel.getWidth()/3 - 30,
                home.getHeight()/4 - 35
        ));
        noteButton.setFont(new Font("aFont", Font.BOLD, 40));


        home.notePanel.add(noteButton);

        return noteButton;
    }

    public static Note createNoteEntryAtIndex(HomeWindow home, String title, String dateCreated, String dateModified, int index) {

        Note noteButton = new Note(title, 0, dateCreated, dateModified);
        noteButton.setText(title);

        noteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Left click
                    home.openNote(noteButton);
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Right click
                    ActionMenuManager.createActionMenu(noteButton, home)
                            .show(noteButton, 10, 10);
                }
            }
        });

        noteButton.setFocusable(false);
        noteButton.setForeground(Color.BLUE);
        noteButton.setBackground(null);
        noteButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        noteButton.setPreferredSize(new Dimension(
                home.notePanel.getWidth()/3 - 30,
                home.getHeight()/4 - 35
        ));
        noteButton.setFont(new Font("aFont", Font.BOLD, 40));


        home.notePanel.add(noteButton, index);

        return noteButton;
    }

    public static void clear(HomeWindow home) {

        home.notePanel.removeAll();
        home.notePanel.revalidate();
        home.notePanel.repaint();

    }

}
