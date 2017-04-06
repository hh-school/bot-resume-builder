package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

import java.util.regex.Pattern;

public class CycleNode implements QuestionGraphNode {
    private Question question;
    private QuestionGraphNode nextIn;
    private QuestionGraphNode nextOut;
    private Pattern answerPattern;
    private boolean matches;

    public CycleNode(Question question, String answerPattern) {
        this.question = question;
        this.answerPattern = Pattern.compile(answerPattern);
    }

    @Override
    public synchronized void registerAnswer(Answer answer) {
        matches = answerPattern.matcher((String) (answer.getAnswerBody())).matches();
    }

    @Override
    public synchronized Question getQuestion() {
        return question;
    }

    @Override
    public synchronized QuestionGraphNode getNext() {
        return matches ? nextIn : nextOut;
    }

    @Override
    public boolean persistData() {
        return false;
    }

    public void setNextIn(QuestionGraphNode nextIn) {
        this.nextIn = nextIn;
    }

    public void setNextOut(QuestionGraphNode nextOut) {
        this.nextOut = nextOut;
    }
}

