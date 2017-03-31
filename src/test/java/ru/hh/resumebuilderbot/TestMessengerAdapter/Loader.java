package ru.hh.resumebuilderbot.TestMessengerAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Loader {

    private final String questionMark = "question=";
    private final String answerMark = "answer=";
    private final String weightMark = "weight=";

    private List<Entry> entries;
    private Entry currentEntry;
    private StringBuilder stringBuilder;

    public Loader(List<Entry> entries) {
        this.entries = entries;
    }

    public void load(String filename) throws IOException {

        Files.lines(Paths.get(filename)).forEach((String line) -> {
            loadLine(line);
        });
    }

    private void loadLine(String line) {
        if (questionBegins(line)) {
            currentEntry = new Entry();
            stringBuilder = new StringBuilder(line.substring(questionMark.length()));
            return;
        }

        if (answerBegins(line)) {
            currentEntry.setPattern(Pattern.compile(stringBuilder.toString()));
            stringBuilder = new StringBuilder(line.substring(answerMark.length()));
            return;
        }

        if (weightBegins(line)) {
            currentEntry.setAnswer(stringBuilder.toString());
            currentEntry.setWeight(Integer.parseInt(line.substring(weightMark.length())));
            entries.add(currentEntry);
            return;
        }

        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(line);
    }

    private boolean questionBegins(String line) {
        return line.startsWith(questionMark);
    }

    private boolean weightBegins(String line) {
        return line.startsWith(weightMark);
    }

    private boolean answerBegins(String line) {
        return line.startsWith(answerMark);
    }
}
