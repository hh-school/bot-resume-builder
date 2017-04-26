package ru.hh.resumebuilderbot.question.storage.graph.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

import java.util.Map;

public class QuestionNodeTerminal implements QuestionNode {
    private Question question;

    public QuestionNodeTerminal() {
        question = new Question(TextsStorage.getText(TextId.FINISHED));
    }

    @Override
    public void setLinks(Map<String, QuestionNode> links) {

    }

    @Override
    public boolean answerIsValid(Answer answer) {
        return true;
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
        return this;
    }

    @Override
    public boolean isSkippable() {
        return true;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeTerminal();
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
        return true;
    }
}
