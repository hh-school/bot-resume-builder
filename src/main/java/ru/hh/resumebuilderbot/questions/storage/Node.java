package ru.hh.resumebuilderbot.questions.storage;

import ru.hh.resumebuilderbot.Question;

public class Node {
    private Question question;

    public Node(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
