package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.Map;

public class QuestionNodeTerminal implements QuestionNode {
    private Question question;
    private int index;

    public QuestionNodeTerminal() {
        question = new Question(TextsStorage.getText(TextId.FINISHED));
    }

    @Override
    public void setLinks(Map<String, QuestionNode> links, Map<String, Integer> indexLinks) {

    }

    @Override
    public String getInvalidAnswerNotification() {
        return null;
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        return true;
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
    public int getNextIndex(Answer answer) {
        return index;
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
    public String getFieldNameToSave() {
        return null;
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
