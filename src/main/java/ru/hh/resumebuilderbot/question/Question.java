package ru.hh.resumebuilderbot.question;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String text;

    private final List<String> allowedAnswers;


    public Question(String text, List<String> allowedAnswers) {
        this.text = text;
        this.allowedAnswers = allowedAnswers;
    }

    public Question(String text) {

        this.text = text;
        this.allowedAnswers = new ArrayList<>();
    }

    public List<String> getAllowedAnswers() {
        return allowedAnswers;
    }

    public String getText() {
        return text;
    }
}
