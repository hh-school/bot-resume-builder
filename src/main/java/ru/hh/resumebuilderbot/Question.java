package ru.hh.resumebuilderbot;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String text;

    private final List<String> allowedAnswers;


    public Question(String text, List<String> allowedAnswers) {
        this.text = text;
        if (allowedAnswers != null)
        {
            this.allowedAnswers = allowedAnswers;
        }
        else
        {
            this.allowedAnswers = new ArrayList<>();
        }
    }

    public Question(String text) {
        this(text, null);
    }

    public List<String> getAllowedAnswers() {
        return allowedAnswers;
    }

    public String getText() {
        return text;
    }
}
