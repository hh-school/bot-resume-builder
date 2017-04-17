package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

public interface QuestionNode {
    boolean answerIsValid(Answer answer);

    void registerAnswer(Answer answer);

    Question getQuestion();

    QuestionNode getNext();

    boolean isSkippable();

    QuestionNode cloneContent();

    void saveAnswer(UserData dest, Answer answer);
}
