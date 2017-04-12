package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

public interface QuestionNode {
    void registerAnswer(Answer answer);

    Question getQuestion();

    QuestionNode getNext();

    boolean needToSaveAnswer();

    QuestionNode cloneContent();
}
