package ru.hh.resumebuilderbot.http.response.entity;

public class Faculty {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Faculty{id='%s', name='%s'}", id, name);
    }
}
