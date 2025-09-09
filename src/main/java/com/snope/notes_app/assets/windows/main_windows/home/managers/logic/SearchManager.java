package com.snope.notes_app.assets.windows.main_windows.home.managers.logic;

import com.snope.notes_app.assets.App;
import com.snope.notes_app.assets.objects.Note;
import com.snope.notes_app.assets.utils.FileUtils;
import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SearchManager {

    private HomeWindow home;

    public SearchManager(HomeWindow home) {
        this.home = home;
    }

    public void search() {

        String searchedText = home.searchField.getText().toLowerCase();

        for (Component component : home.notePanel.getComponents()) {

            try {

                Note note = (Note) component;

                if (contains(note, searchedText)) {
                    if (!note.isVisible()) note.setVisible(true);
                    continue;
                }
                note.setVisible(false);

            } catch (IOException e) {
                System.out.println("Couldn't find file '" + App.PATH + "notes/" + ( (Note) component ).getTitle() + ".txt'.");
            }

        }

    }

    public void resetSearch() {
        home.searchField.setText(null);
        home.requestFocusInWindow();
    }

    private boolean contains(Note note, String searchedText) throws IOException {

        boolean containsInTitle = note.getTitle().toLowerCase().contains(searchedText);
        boolean containsInContent = FileUtils.readFileAsStr(new File(App.PATH + "notes/" + note.getTitle() + ".txt")).toLowerCase().contains(searchedText);

        return containsInTitle || containsInContent;

    }

}
