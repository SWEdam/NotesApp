package com.snope.notes_app.assets.windows.main_windows.home.managers.logic;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snope.notes_app.assets.App;
import com.snope.notes_app.assets.data_managers.Metadata.NoteMetadata;
import com.snope.notes_app.assets.data_managers.enums.SortOptions;
import com.snope.notes_app.assets.data_managers.enums.SortOrders;
import com.snope.notes_app.assets.windows.main_windows.home.HomeWindow;
import com.snope.notes_app.assets.windows.main_windows.home.managers.ui.NoteUIManager;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class SortManager {

    private static HomeWindow home;
    private static SearchManager searchManager;

    public SortOptions[] sortOptionArray = {
            SortOptions.DATE_MODIFIED,
            SortOptions.DATE_CREATED,
            SortOptions.LEXICOGRAPHICALLY
    };
    public SortOrders[] sortOrderArray = {
            SortOrders.DESCENDING,
            SortOrders.ASCENDING
    };

    public SortManager(HomeWindow home) {
        this.home = home;
        searchManager = new SearchManager(home);

        home.sortOptionBox = new JComboBox<>(sortOptionArray);
        home.sortOrderBox = new JComboBox<>(sortOrderArray);

        home.sortOptionBox.setSelectedItem(SortOptions.valueOf(App.settingsNode.get("sort_option").asText()));
        home.sortOrderBox.setSelectedItem(SortOrders.valueOf(App.settingsNode.get("sort_order").asText()));
    }

    public static void updateOption(JComboBox<SortOptions> box) throws IOException {
        ObjectNode newJson = (ObjectNode) App.settingsNode;
        newJson.put("sort_option", box.getSelectedItem().toString());
        App.mapper.writeValue(App.settingsFile, newJson);
    }

    public static void updateOrder(JComboBox<SortOrders> box) throws IOException {
        ObjectNode newJson = (ObjectNode) App.settingsNode;
        newJson.put("sort_order", box.getSelectedItem().toString());
        App.mapper.writeValue(App.settingsFile, newJson);
    }

    public static SortOptions getSortOption() {
        return (SortOptions) home.sortOptionBox.getSelectedItem();
    }

    public static SortOrders getSortOrder() {
        return (SortOrders) home.sortOrderBox.getSelectedItem();
    }

    public static void sort() {

        if (App.metadataContainer.getNotes().isEmpty()) return;

        @SuppressWarnings("unchecked")
        Map.Entry<String, NoteMetadata>[] entries =
                App.metadataContainer.getNotes().entrySet().toArray(new Map.Entry[0]);

        Comparator<Map.Entry<String, NoteMetadata>> comparator = getComparator();

        Arrays.sort(entries, comparator);

        App.metadataContainer.getNotes().clear();
        for (Map.Entry<String, NoteMetadata> entry : entries) {
            App.metadataContainer.addNote(entry);
        }

        searchManager.resetSearch();

    }

    private static Comparator<Map.Entry<String, NoteMetadata>> getComparator() {
        Comparator<Map.Entry<String, NoteMetadata>> baseComparator;

        switch (getSortOption()) {
            case DATE_CREATED -> baseComparator = Comparator.comparing(
                    entry -> LocalDateTime.parse(entry.getValue().getDate_created())
            );
            case DATE_MODIFIED -> baseComparator = Comparator.comparing(
                    entry -> LocalDateTime.parse(entry.getValue().getDate_modified())
            );
            case LEXICOGRAPHICALLY -> baseComparator = Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER);
            default -> throw new IllegalStateException("Unknown sort option: " + getSortOption());
        }

        return getSortOrder() == SortOrders.ASCENDING ? baseComparator : baseComparator.reversed();

    }

    public static void sortAndUpdateUI() throws IOException {
        sort();
        saveMetadataAndUpdateUI();
    }

    private static void saveMetadataAndUpdateUI() throws IOException {

        App.mapper.writeValue(App.metadataFile, App.metadataContainer);
        App.metadataNode = App.mapper.readTree(App.metadataFile);

        NoteUIManager.clear(home);
        for (Map.Entry<String, NoteMetadata> entry : App.metadataContainer.getNotes().entrySet()) {
            NoteUIManager.createNoteEntry(
                    home,
                    entry.getKey(),
                    entry.getValue().getDate_created(),
                    entry.getValue().getDate_modified()
            );
        }
        home.notePanel.revalidate();
        home.notePanel.repaint();

    }

    public static void updateOptionMetadata() throws IOException {

        if (App.metadataContainer.getNotes().isEmpty()) return;

        ObjectNode settingsJson = (ObjectNode) App.settingsNode;
        String oldOption = settingsJson.get("sort_option").asText();
        String newOption = getSortOption().toString();

        if (newOption.equals(oldOption)) return;

        sortAndUpdateUI();

    }

    public static void updateOrderMetadata() throws IOException {

        if (App.metadataContainer.getNotes().isEmpty()) return;

        ObjectNode settingsJson = (ObjectNode) App.settingsNode;
        String oldOrder = settingsJson.get("sort_order").asText();
        String newOrder = getSortOrder().toString();

        if (newOrder.equals(oldOrder)) return;

        reverseCurrentOrder();
        saveMetadataAndUpdateUI();

    }

    private static void reverseCurrentOrder() {

        @SuppressWarnings("unchecked")
        Map.Entry<String, NoteMetadata>[] entries =
                App.metadataContainer.getNotes().entrySet().toArray(new Map.Entry[0]);

        App.metadataContainer.getNotes().clear();

        for (int i = entries.length - 1; i >= 0; i--) {
            App.metadataContainer.addNote(entries[i]);
        }

    }

}