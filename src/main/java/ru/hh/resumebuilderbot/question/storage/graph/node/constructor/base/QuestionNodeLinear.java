package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver.Saver;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator.Validator;

import java.util.Map;
import java.util.Objects;

public class QuestionNodeLinear extends QuestionNodeNonTerminal {
    private QuestionNode next;

    public QuestionNodeLinear(Question question, boolean isSkippable) {
        this.question = question;
        this.skippable = isSkippable;
    }

    private QuestionNodeLinear(Question question, boolean isSkippable, Validator validator, Saver saver) {
        this.question = question;
        this.skippable = isSkippable;
        this.validator = validator;
        this.saver = saver;
    }

    public QuestionNodeLinear() {
    }

    @Override
    public void setLinks(Map<String, QuestionNode> links, Map<String, Integer> indexLinks) {
        next = links.get("next");
        this.indexLinks = indexLinks;
    }

    @Override
    public QuestionNode getNext() {
        return next;
    }

    @Override
    public int getNextIndex(Answer answer) {
        return indexLinks.get("next");
    }

    @Override
    public boolean isSkippable() {
        return skippable;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeLinear(question, skippable, validator.clone(), saver.clone());
    }

    @Override
    public String getFieldNameToSave() {
        return saver.getDatabaseField();
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
        return super.hasEqualContent(questionNode) &&
                Objects.equals(question, that.question) &&
                that.skippable == skippable;
    }
}
