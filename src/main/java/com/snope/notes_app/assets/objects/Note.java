package com.snope.notes_app.assets.objects;

import javax.swing.*;
import java.time.LocalDateTime;

public class Note extends JButton {

    private String title;
    private int contentSize;
    private String dateCreated;
    private String dateModified;

    public Note(String title, int contentSize, String  dateCreated, String dateModified) {
        this.title = title;
        this.contentSize = contentSize;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
        setText(title);
    }

    public int getContentSize() { return contentSize; }
    public void setContentSize(int contentSize) { this.contentSize = contentSize; }

    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    public String getDateModified() { return dateModified; }
    public void setDateModified(String dateModified) { this.dateModified = dateModified; }


}
