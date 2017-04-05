package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;

import java.util.regex.Pattern;

public class ForkingNode implements QuestionGraphNode {
    private Question question;
    private QuestionGraphNode nextYes;
    private QuestionGraphNode nextNo;
    private Pattern answerPattern;
    private boolean matches;


    public ForkingNode(Question question, String answerPattern) {
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
        return matches ? nextYes : nextNo;
    }

    public void setNextYes(QuestionGraphNode nextYes) {
        this.nextYes = nextYes;
    }

    public void setNextNo(QuestionGraphNode nextNo) {
        this.nextNo = nextNo;
    }
}
