package ru.hh.resumebuilderbot.question;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String text;

    private final List<String> variantsOfAnswer;


    public Question(String text, List<String> variantsOfAnswer) {
        this.text = text;
        this.variantsOfAnswer = variantsOfAnswer;
    }

    public Question(String text) {

        this.text = text;
        this.variantsOfAnswer = new ArrayList<>();
    }

    public List<String> getVariantsOfAnswer() {
        return variantsOfAnswer;
    }

    public String getText() {
        return text;
    }
}
