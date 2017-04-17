package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

public class QuestionNodeTerminal implements QuestionNode {
    private Question question;

    public QuestionNodeTerminal() {
        question = new Question(TextsStorage.getText(TextId.FINISHED));
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
    public boolean needToSaveAnswer() {
        return false;
    }

    @Override
    public boolean isSkippable() {
        return true;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeTerminal();
    }
}
