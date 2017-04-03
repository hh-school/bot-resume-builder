package ru.hh.resumebuilderbot.question.storage.node;

import java.util.regex.Pattern;

public class ForkingNode extends NonTerminalNode {
    private Node nextYes;
    private Node nextNo;
    private Pattern answerPattern;
    private boolean matches;


    public ForkingNode(String answerPattern, Node nextYes, Node nextNo) {
        this.nextYes = nextYes;
        this.nextNo = nextNo;
        this.answerPattern = Pattern.compile(answerPattern);
    }

    @Override
    public synchronized Node getNext() {
        return matches ? nextYes : nextNo;
    }

    public synchronized void check(String currentAnswer) {
        matches = answerPattern.matcher(currentAnswer).matches();
    }
}
