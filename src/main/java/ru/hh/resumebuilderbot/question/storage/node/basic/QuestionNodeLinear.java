package ru.hh.resumebuilderbot.question.storage.node.basic;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

import java.util.Map;

public class QuestionNodeLinear implements QuestionNode {
    private Question question;
    private boolean isSkippable;
    private QuestionNode next;

    public QuestionNodeLinear(Question question, boolean isSkippable) {
        this.question = question;
        this.isSkippable = isSkippable;
    }

    @Override
    public void setLinks(Map<String, QuestionNode> links) {
        next = links.get("next");
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

    @Override
    public boolean isSkippable() {
        return isSkippable;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeLinear(question, isSkippable);
    }

    @Override
    public void saveAnswer(UserData dest, Answer answer) {

    }
}
