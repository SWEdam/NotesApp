package com.snope.notes_app.assets.data_managers.Metadata;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MetadataContainer {

    @JsonManagedReference
    @JsonProperty("notes")
    private Map<String, NoteMetadata> notes = new LinkedHashMap<>();

    private int count = 0;

    public MetadataContainer() {}

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public Map<String, NoteMetadata> getNotes() { return notes; }
    public void setNotes(Map<String, NoteMetadata> notes) {

        this.notes = notes;

        if (notes == null) return;

        for (Map.Entry<String, NoteMetadata> entry : notes.entrySet()) {
            entry.getValue().setParent(this);
            entry.getValue().setNoteTitle(entry.getKey());
        }

    }

    public void addNote(String noteTitle, NoteMetadata noteMetadata) {

        noteMetadata.setParent(this);
        noteMetadata.setNoteTitle(noteTitle);
        this.notes.put(noteTitle, noteMetadata);

    }
    public void addNote(Map.Entry<String, NoteMetadata> entry) {

        entry.getValue().setParent(this);
        entry.getValue().setNoteTitle(entry.getKey());
        this.notes.put(entry.getKey(), entry.getValue());

    }

    public NoteMetadata getNote(String noteTitle) { return notes.get(noteTitle); }



}
