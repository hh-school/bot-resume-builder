package ru.hh.resumebuilderbot.question.storage.graph.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

import java.util.Map;

public interface QuestionNode {

    void setLinks(Map<String, QuestionNode> links);

    boolean answerIsValid(Answer answer);

    void registerAnswer(Answer answer);

    Question getQuestion();

    QuestionNode getNext();

    boolean isSkippable();

    QuestionNode cloneContent();

    void saveAnswer(UserData dest, Answer answer);
}
