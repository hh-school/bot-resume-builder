package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

public class LinearNode implements QuestionGraphNode {
    private Question question;
    private QuestionGraphNode next;

    public LinearNode(Question question) {
        this.question = question;
    }

    @Override
    public synchronized void registerAnswer(Answer answer) {

    }

    @Override
    public synchronized Question getQuestion() {
        return question;
    }

    @Override
    public synchronized QuestionGraphNode getNext() {
        return next;
    }

    public synchronized void setNext(QuestionGraphNode next) {
        this.next = next;
    }
}
