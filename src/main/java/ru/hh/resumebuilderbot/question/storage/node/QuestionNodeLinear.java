package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

public class QuestionNodeLinear implements QuestionNode {
    private Question question;
    private boolean isSkippable;
    private QuestionNode next;

    public QuestionNodeLinear(Question question, boolean isSkippable) {
        this.question = question;
        this.isSkippable = isSkippable;
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        return question.answerIsAllowed(answer);
    }

    @Override
    public void registerAnswer(Answer answer) {

    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public QuestionNode getNext() {
        return next;
    }

    public void setNext(QuestionNode next) {
        this.next = next;
    }

    @Override
    public boolean needToSaveAnswer() {
        return true;
    }

    @Override
    public boolean isSkippable() {
        return isSkippable;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeLinear(question, isSkippable);
    }
}
