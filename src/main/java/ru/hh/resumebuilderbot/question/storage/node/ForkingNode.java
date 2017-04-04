package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.question.Question;

import java.util.regex.Pattern;

public class ForkingNode extends NonTerminalNode {
    private Node nextYes;
    private Node nextNo;
    private Pattern answerPattern;
    private boolean matches;


    public ForkingNode(Question question, String answerPattern) {
        this.question = question;
        this.answerPattern = Pattern.compile(answerPattern);
    }

    @Override
    public synchronized Node getNext() {
        return matches ? nextYes : nextNo;
    }

    public synchronized void check(String currentAnswer) {
        matches = answerPattern.matcher(currentAnswer).matches();
    }

    public void setNextYes(Node nextYes) {
        this.nextYes = nextYes;
    }

    public void setNextNo(Node nextNo) {
        this.nextNo = nextNo;
    }
}
