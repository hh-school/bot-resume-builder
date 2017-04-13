package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

import java.util.regex.Pattern;

public class QuestionNodeCycle implements QuestionNode {
    private Question question;
    private QuestionNode nextIn;
    private QuestionNode nextOut;
    private Pattern answerPattern;
    private boolean matches;

    public QuestionNodeCycle(Question question, String answerPattern) {
        this.question = question;
        this.answerPattern = Pattern.compile(answerPattern);
    }

    private QuestionNodeCycle(Question question, Pattern pattern) {
        this.question = question;
        this.answerPattern = pattern;
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
        return matches ? nextIn : nextOut;
    }

    @Override
    public boolean needToSaveAnswer() {
        return false;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeCycle(question, answerPattern);
    }

    public void setNextIn(QuestionNode nextIn) {
        this.nextIn = nextIn;
    }

    public void setNextOut(QuestionNode nextOut) {
        this.nextOut = nextOut;
    }
}

