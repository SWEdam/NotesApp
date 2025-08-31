package com.snope.notes_app.assets.data_managers.Metadata;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.snope.notes_app.assets.App;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class NoteMetadata {

    private String dateCreated = LocalDateTime.now().format(App.dateTimeFormatter);
    private String dateModified = LocalDateTime.now().format(App.dateTimeFormatter);

    // Parent reference - won't be serialized to JSON
    @JsonBackReference
    private MetadataContainer parent;

    // Note name for reference (not serialized as it's the key in the parent map)
    private String noteTitle;

    public NoteMetadata() {}

    public NoteMetadata(String date) {
        dateCreated = date;
        dateModified = date;
    }

    public String getDate_created() { return dateCreated; }
    public void setDate_created(String dateCreated) { this.dateCreated = dateCreated; }

    public String getDate_modified() { return dateModified; }
    public void setDate_modified(String dateModified) { this.dateModified = dateModified; }

    public MetadataContainer getParent() { return parent; }
    public void setParent(MetadataContainer parent) { this.parent = parent; }

    @JsonIgnore
    public String getNoteTitle() { return noteTitle; }
    public void setNoteTitle(String noteTitle) { this.noteTitle = noteTitle; }

    // Utility method to access sibling notes through parent
    @JsonIgnore
    public NoteMetadata getSiblingNote(String siblingName) {

        if (parent == null) return null;
        return parent.getNote(siblingName);

    }

    // Utility method to get all sibling notes
    @JsonIgnore
    public Map<String, NoteMetadata> getAllSiblingNotes() {

        if (parent == null) return new HashMap<>();
        return parent.getNotes();

    }

}
