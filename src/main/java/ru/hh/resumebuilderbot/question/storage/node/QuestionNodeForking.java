package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

import java.util.regex.Pattern;

public class QuestionNodeForking implements QuestionNode {
    private Question question;
    private QuestionNode nextYes;
    private QuestionNode nextNo;
    private Pattern answerPattern;
    private boolean isSkippable;
    private boolean matches;


    public QuestionNodeForking(Question question, String answerPattern, boolean isSkippable) {
        this.question = question;
        this.answerPattern = Pattern.compile(answerPattern);
        this.isSkippable = isSkippable;
    }

    private QuestionNodeForking(Question question, Pattern pattern, boolean isSkippable) {
        this.question = question;
        this.answerPattern = pattern;
        this.isSkippable = isSkippable;
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        return question.answerIsAllowed(answer);
    }

    @Override
    public void registerAnswer(Answer answer) {
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
    public boolean isSkippable() {
        return isSkippable;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeForking(question, answerPattern, isSkippable);
    }

    public void setNextYes(QuestionNode nextYes) {
        this.nextYes = nextYes;
    }

    public void setNextNo(QuestionNode nextNo) {
        this.nextNo = nextNo;
    }
}
