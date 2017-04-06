package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

public interface QuestionGraphNode {
    void registerAnswer(Answer answer);

    Question getQuestion();

    QuestionGraphNode getNext();

    boolean persistData();

}
