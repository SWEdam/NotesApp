package com.snope.notes_app.assets.windows.main_windows.home.managers.ui;

import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/*
   Warning: This class is an implementation for the Home class.
   - Do not use directly. Use Home's public methods instead.

 */

public class UIManager {

    private HomeWindow home;

    public UIManager(HomeWindow home) {
        this.home = home;
    }

    public void setupUI() {

        setupWelcomeText();
        setupNewButton();
        setupScrollPane();
        setupSortButtons();
        setupSearchField();
        setupImportButton();

    }

    private void setupWelcomeText() {

        HomeWindow.welcomeText.setForeground(Color.LIGHT_GRAY);
        HomeWindow.welcomeText.setFont(new Font("aFont", Font.BOLD, 25));
        HomeWindow.welcomeText.setSize(380, 100);
        HomeWindow.welcomeText.setLocation(
                home.getWidth()/2 - HomeWindow.welcomeText.getWidth()/2,
                home.getHeight()/2 - HomeWindow.welcomeText.getHeight()/2
        );

    }

    private void setupNewButton() {

        home.newButtonPanel.setBounds(home.getWidth() - 130, 18, 100, 100);
        home.newButtonPanel.setLayout(null);
        home.newButtonPanel.setBackground(null);

        home.newButton.setSize(home.newButtonPanel.getSize());
        home.newButton.setFocusable(false);
        home.newButton.setBorder(new LineBorder(Color.BLUE, 3));
        home.newButton.setForeground(Color.BLUE);
        home.newButton.setBackground(Color.BLACK);
        home.newButton.setFont(new Font("aFont", Font.BOLD, 40));

        home.newButtonPanel.add(home.newButton);

    }

    private void setupScrollPane() {

        home.scrollPane.setBounds(100, 100, home.getWidth() - 200, home.getHeight() - 200);
        home.scrollPane.getViewport().getView().setBackground(Color.BLACK);
        home.scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

        home.scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.DARK_GRAY;
                this.trackColor = Color.DARK_GRAY.darker();
                this.thumbLightShadowColor = Color.BLACK;
                this.thumbDarkShadowColor = Color.BLACK;
            }
        });

    }

    private void setupSortButtons() {

        home.sortOptionBox.setBounds(home.scrollPane.getX(), home.scrollPane.getY() - 35, 160, 35);
        home.sortOptionBox.setFocusable(false);
        home.sortOptionBox.setBorder(new LineBorder(Color.BLUE, 2));
        home.sortOptionBox.setForeground(Color.GRAY);
        home.sortOptionBox.setBackground(null);
        home.sortOptionBox.setFont(new Font("aFont", Font.BOLD, 15));

        home.sortOrderBox.setBounds(home.sortOptionBox.getX() + home.sortOptionBox.getWidth(), home.sortOptionBox.getY(), 135, 35);
        home.sortOrderBox.setFocusable(false);
        home.sortOrderBox.setBorder(new LineBorder(Color.BLUE, 2));
        home.sortOrderBox.setForeground(Color.GRAY);
        home.sortOrderBox.setBackground(null);
        home.sortOrderBox.setFont(new Font("aFont", Font.BOLD, 15));

    }

    private void setupSearchField() {

        home.searchField.setBounds(home.sortOrderBox.getX() + home.sortOrderBox.getWidth(), home.sortOrderBox.getY(), 400, 35);
        home.searchField.setBorder(new LineBorder(Color.BLUE, 2));
        home.searchField.setForeground(Color.WHITE);
        home.searchField.setBackground(null);
        home.searchField.setFont(new Font("aFont", Font.BOLD, 15));
        home.searchField.setMargin(new Insets(10, 10, 10, 10));
        home.searchField.setCaretColor(Color.LIGHT_GRAY);
        home.searchField.setSelectedTextColor(Color.LIGHT_GRAY);
        home.searchField.setSelectionColor(Color.BLUE);

    }

    private void setupImportButton() {

        home.importButton.setBounds(home.getWidth() - home.newButtonPanel.getWidth() - 150, 50, 100, 30);
        home.importButton.setBorder(new LineBorder(Color.BLUE, 2));
        home.importButton.setForeground(Color.BLUE);
        home.importButton.setBackground(null);
        home.importButton.setFont(new Font("aFont", Font.BOLD, 20));
        home.importButton.setMargin(new Insets(10, 10, 10, 10));
        home.importButton.setFocusable(false);

    }

}