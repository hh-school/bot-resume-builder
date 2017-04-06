package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

public class TerminalNode implements QuestionGraphNode {
    private Question question;

    public TerminalNode() {
        question = new Question(TextsStorage.getText(TextId.FINISHED));
    }

    @Override
    public void registerAnswer(Answer answer) {

    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public QuestionGraphNode getNext() {
        return this;
    }

    @Override
    public boolean persistData() {
        return false;
    }
}
