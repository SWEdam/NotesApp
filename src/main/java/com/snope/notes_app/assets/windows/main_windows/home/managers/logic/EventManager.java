package com.snope.notes_app.assets.windows.main_windows.home.managers.logic;

import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/*
   Warning: This class is an implementation for the Home class.
   - Do not use directly. Use Home's public methods instead.

 */

public class EventManager {

    private HomeWindow home;

    public EventManager(HomeWindow home) {
        this.home = home;
    }

    public void setupListeners() {

        setupNewButtonListener();
        setupScrollPaneListener();
        setupSortButtonsListeners();

    }

    private void setupNewButtonListener() {

        home.newButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                home.newButton.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Left click
                    home.createNewNote();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                home.newButton.setBackground(null);
            }
        });

    }

    private void setupScrollPaneListener() {

        home.scrollPane.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Repaint the NewButtonPanel when ScrollPane UI goes over it
                home.newButtonPanel.repaint();
            }
        });

    }

    private void setupSortButtonsListeners() {

        home.sortOptionBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() != ItemEvent.SELECTED) return;

                try {

                    SortManager.updateOptionMetadata();
                    SortManager.updateOption(home.sortOptionBox);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        home.sortOrderBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() != ItemEvent.SELECTED) return;

                try {

                    SortManager.updateOrderMetadata();
                    SortManager.updateOrder(home.sortOrderBox);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }

}