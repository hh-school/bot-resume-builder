package ru.hh.resumebuilderbot.telegram.handler.suggest;

public class Notification {
    private String description;
    private String text;
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("Specialization{description='%s', text='%s', title='%s'}", description, text, title);
    }
}
