package ru.hh.resumebuilderbot.http.response.entity;

public class Area {
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("Area{id='%s', text='%s'}", id, text);
    }
}
