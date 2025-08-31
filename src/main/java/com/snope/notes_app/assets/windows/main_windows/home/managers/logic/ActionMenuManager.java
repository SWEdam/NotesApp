package com.snope.notes_app.assets.windows.main_windows.home.managers.logic;

import com.snope.notes_app.assets.objects.Note;
import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;

import javax.swing.*;
import java.awt.*;

/*
   Warning: This class is an implementation for the Home class.
   - Do not use directly. Use Home's public methods instead.

 */

public class ActionMenuManager {

    public static JPopupMenu createActionMenu(Note noteButton, HomeWindow home) {

        JPopupMenu actionMenu = new JPopupMenu();

        JMenuItem deleteAction = new JMenuItem("Delete note");
        JMenuItem renameAction = new JMenuItem("Rename note");

        deleteAction.addActionListener(event -> home.deleteNote(noteButton));
        renameAction.addActionListener(event -> home.renameNote(noteButton));

        actionMenu.setBackground(Color.DARK_GRAY);
        actionMenu.setBorderPainted(false);

        styleMenuItem(deleteAction);
        styleMenuItem(renameAction);
        actionMenu.add(deleteAction);
        actionMenu.add(renameAction);

        return actionMenu;

    }

    private static void styleMenuItem(JMenuItem menuItem) {

        menuItem.setBackground(Color.DARK_GRAY);
        menuItem.setForeground(Color.WHITE.darker());
        menuItem.setBorderPainted(false);

    }

}