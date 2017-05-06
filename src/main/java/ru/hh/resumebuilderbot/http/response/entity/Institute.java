package ru.hh.resumebuilderbot.http.response.entity;

public class Institute {
    private String id;
    private String acronym;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("Institute{id='%s', acronym='%s', text='%s'}", id, acronym, text);
    }
}
