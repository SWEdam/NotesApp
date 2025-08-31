package com.snope.notes_app.assets.objects;

public record TextState(String text, int caretPos) {

    public TextState(String text, int caretPos) {

        this.text = text;
        this.caretPos = Math.min(Math.max(0, caretPos), text.length());

    }

}
