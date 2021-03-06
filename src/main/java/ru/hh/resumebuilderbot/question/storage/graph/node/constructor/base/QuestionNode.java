package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

import java.util.Map;

public interface QuestionNode {

    void setLinks(Map<String, QuestionNode> links, Map<String, Integer> indexLinks);

    boolean answerIsValid(Answer answer);

    Question getQuestion();

    QuestionNode getNext();

    String getInvalidAnswerNotification();

    int getNextIndex(Answer answer);

    boolean isSkippable();

    QuestionNode cloneContent();

    String getFieldNameToSave();

    boolean hasEqualContent(QuestionNode questionNode);
}
