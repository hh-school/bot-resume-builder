package ru.hh.resumebuilderbot.http.request.entity;

import com.google.gson.annotations.SerializedName;

public class Negotiation {
    @SerializedName("vacancy_id")
    private String vacancyId;
    @SerializedName("resume_id")
    private String resumeId;
    private String message;

    public String getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(String vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Negotiation{vacancyId='%s', resumeId='%s', message='%s'}", vacancyId, resumeId, message);
    }
}
