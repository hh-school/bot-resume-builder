package ru.hh.resumebuilderbot.question.storage.graph.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean hasEqualContent(QuestionNode questionNode) {

        if (this == questionNode) {
            return true;
        }
        if (questionNode == null || getClass() != questionNode.getClass()) {
            return false;
        }
        QuestionNodeLinear that = (QuestionNodeLinear) questionNode;

        return Objects.equals(question, that.question) && that.isSkippable == isSkippable;
    }
}
