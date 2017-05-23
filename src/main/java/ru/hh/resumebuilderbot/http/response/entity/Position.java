package ru.hh.resumebuilderbot.http.response.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Position {
    private String id;
    @SerializedName(value = "text")
    private String name;
    private List<Specialization> specializations = new ArrayList<>();

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

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }

    @Override
    public String toString() {
        return String.format("Position{id='%s', name='%s'}", id, name);
    }
}
