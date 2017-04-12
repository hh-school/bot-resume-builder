package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

import java.util.regex.Pattern;

public class QuestionNodeForking implements QuestionNode {
    private Question question;
    private QuestionNode nextYes;
    private QuestionNode nextNo;
    private Pattern answerPattern;
    private boolean matches;


    public QuestionNodeForking(Question question, String answerPattern) {
        this.question = question;
        this.answerPattern = Pattern.compile(answerPattern);
    }

    private QuestionNodeForking(Question question, Pattern pattern) {
        this.question = question;
        this.answerPattern = pattern;
    }

    @Override
    public void checkAnswer(Answer answer) {
        matches = answerPattern.matcher((String) (answer.getAnswerBody())).matches();
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public QuestionNode getNext() {
        return matches ? nextYes : nextNo;
    }

    @Override
    public boolean needToSaveAnswer() {
        return true;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeForking(question, answerPattern);
    }

    public void setNextYes(QuestionNode nextYes) {
        this.nextYes = nextYes;
    }

    public void setNextNo(QuestionNode nextNo) {
        this.nextNo = nextNo;
    }
}
