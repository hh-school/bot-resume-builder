package ru.hh.resumebuilderbot.telegram.handler.suggest.exceptions;

public class NoSuggestsFoundException extends Exception {
    private String textForSearch;

    public NoSuggestsFoundException(String textForSearch) {
        this.textForSearch = textForSearch;
    }

    public String getTextForSearch() {
        return textForSearch;
    }
}
