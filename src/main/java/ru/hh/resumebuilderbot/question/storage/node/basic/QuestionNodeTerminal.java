package ru.hh.resumebuilderbot.question.storage.node.basic;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
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
}
