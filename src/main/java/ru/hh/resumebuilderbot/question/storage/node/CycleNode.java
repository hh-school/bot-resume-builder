package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

import java.util.regex.Pattern;

public class CycleNode implements QuestionNode {
    private Question question;
    private QuestionNode nextIn;
    private QuestionNode nextOut;
    private Pattern answerPattern;
    private boolean matches;

    public CycleNode(Question question, String answerPattern) {
        this.question = question;
        this.answerPattern = Pattern.compile(answerPattern);
    }

    @Override
    public synchronized void checkAnswer(Answer answer) {
        matches = answerPattern.matcher((String) (answer.getAnswerBody())).matches();
    }

    @Override
    public synchronized Question getQuestion() {
        return question;
    }

    @Override
    public synchronized QuestionNode getNext() {
        return matches ? nextIn : nextOut;
    }

    @Override
    public boolean needToSaveAnswer() {
        return false;
    }

    public void setNextIn(QuestionNode nextIn) {
        this.nextIn = nextIn;
    }

    public void setNextOut(QuestionNode nextOut) {
        this.nextOut = nextOut;
    }
}

