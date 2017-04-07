package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

public class LinearNode implements QuestionNode {
    private Question question;
    private QuestionNode next;

    public LinearNode(Question question) {
        this.question = question;
    }

    @Override
    public synchronized void checkAnswer(Answer answer) {

    }

    @Override
    public synchronized Question getQuestion() {
        return question;
    }

    @Override
    public synchronized QuestionNode getNext() {
        return next;
    }

    public synchronized void setNext(QuestionNode next) {
        this.next = next;
    }

    @Override
    public boolean needToSaveAnswer() {
        return true;
    }
}
