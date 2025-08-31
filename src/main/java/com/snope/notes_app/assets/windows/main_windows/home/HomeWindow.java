package com.snope.notes_app.assets.windows.main_windows.home;

import com.snope.notes_app.assets.data_managers.enums.SortOptions;
import com.snope.notes_app.assets.data_managers.enums.SortOrders;
import com.snope.notes_app.assets.objects.Note;
import com.snope.notes_app.assets.utils.JFrameUtils;
import com.snope.notes_app.assets.windows.main_windows.home.managers.logic.EventManager;
import com.snope.notes_app.assets.windows.main_windows.home.managers.logic.NoteManager;
import com.snope.notes_app.assets.windows.main_windows.home.managers.logic.SortManager;
import com.snope.notes_app.assets.windows.main_windows.home.managers.ui.LayoutManager;
import com.snope.notes_app.assets.windows.main_windows.home.managers.ui.NoteUIManager;
import com.snope.notes_app.assets.windows.main_windows.home.managers.ui.UIManager;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class HomeWindow extends JFrame {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 780;
    public static final int COMPONENT_GAP = 22;

    public static JLabel welcomeText = new JLabel("Start by creating your first note!", JLabel.CENTER);
    public JButton newButton = new JButton("+");
    public JPanel newButtonPanel = new JPanel();
    public JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, COMPONENT_GAP, COMPONENT_GAP)) {
        @Override
        public Dimension getPreferredSize() {
            return LayoutManager.forcePanelColumnLimit(this, 3);
        }
    };
    public JScrollPane scrollPane = new JScrollPane(notePanel, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
    public JComboBox<SortOptions> sortOptionBox;
    public JComboBox<SortOrders> sortOrderBox;

    private UIManager uiManager;
    private EventManager eventManager;
    public NoteManager noteManager;
    private SortManager sortManager;

    public HomeWindow() {

        uiManager = new UIManager(this);
        eventManager = new EventManager(this);
        noteManager = new NoteManager(this);
        sortManager = new SortManager(this);

        JFrameUtils.setupFrame(this, "Notes", DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        uiManager.setupUI();
        eventManager.setupListeners();

        JFrameUtils.addComponentsTo(this, welcomeText, newButtonPanel, scrollPane, sortOptionBox, sortOrderBox);
        this.pack();
        this.setVisible(true);

    }

    public void createNewNote() {
        noteManager.createNewNote();
    }

    public void openNote(Note noteButton) {
        noteManager.openNote(noteButton);
    }

    public void deleteNote(Note noteButton) {
        noteManager.deleteNote(noteButton);
    }

    public void renameNote(Note noteButton) {
        noteManager.renameNote(noteButton, null);
    }

    public static void hideWelcomeText() {
        welcomeText.setVisible(false);
    }

    public static void showWelcomeText() {
        welcomeText.setVisible(true);
    }

}

