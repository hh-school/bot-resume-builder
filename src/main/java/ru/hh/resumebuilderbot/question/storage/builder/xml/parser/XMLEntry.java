package ru.hh.resumebuilderbot.question.storage.builder.xml.parser;

import java.util.List;

public class XMLEntry {
    private int index;
    private String type;
    private int nextIndex;
    private String text;
    private List<String> allowedAnswers;
    private String pattern;
    private int nextYes;
    private int nextNo;
    private boolean isRoot;

    XMLEntry(int index, int nextIndex, String text, List<String> allowedAnswers) {
        this.index = index;
        this.type = "linear";
        this.nextIndex = nextIndex;
        this.text = text;
        this.allowedAnswers = allowedAnswers;
    }

    XMLEntry(String type, int index, String text, List<String> allowedAnswers, String pattern, int nextYes,
             int nextNo) {
        this.index = index;
        this.type = type;
        this.text = text;
        this.allowedAnswers = allowedAnswers;
        this.pattern = pattern;
        this.nextYes = nextYes;
        this.nextNo = nextNo;
    }

    XMLEntry(int index) {
        this.type = "terminal";
        this.index = index;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public int getIndex() {
        return index;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public String getText() {
        return text;
    }

    public List<String> getAllowedAnswers() {
        return allowedAnswers;
    }

    public String getType() {
        return type;
    }

    public String getPattern() {
        return pattern;
    }

    public int getNextYes() {
        return nextYes;
    }

    public int getNextNo() {
        return nextNo;
    }
}
