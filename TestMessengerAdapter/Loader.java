import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Trympyrym on 10.03.2017.
 */
public class Loader {

    private final String questionMark = "question=";
    private final String answerMark = "answer=";
    private final String weightMark = "weight=";

    private List<Entry> entries;
    private Entry currentEntry;
    private State currentState;
    private StringBuilder stringBuilder;

    private enum State
    {
        BEFORE_READING_QUESTION, READING_QUESTION, READING_ANSWER
    }

    public Loader(List<Entry> entries) {
        this.entries = entries;
    }

    public void load(String filename) throws IOException {
        prepareLoading();
        Files.lines(Paths.get(filename)).forEach((String line) -> {loadLine(line);});
    }

    private void prepareLoading()
    {
        currentState = State.BEFORE_READING_QUESTION;
    }

    private void loadLine(String line)
    {
        if (currentState == State.BEFORE_READING_QUESTION)
        {
            if (questionBegins(line))
            {
                currentState = State.READING_QUESTION;
                currentEntry = new Entry();
                stringBuilder = new StringBuilder(line.substring(questionMark.length()));
            }
        }

        else if (currentState == State.READING_QUESTION)
        {
            if (answerBegins(line))
            {
                currentState = State.READING_ANSWER;
                currentEntry.setPattern(Pattern.compile(stringBuilder.toString()));
                stringBuilder = new StringBuilder(line.substring(answerMark.length()));
            }
            else
            {
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(line);
            }
        }

        else
        {
            if (weightBegins(line))
            {
                currentState = State.BEFORE_READING_QUESTION;
                currentEntry.setAnswer(stringBuilder.toString());
                currentEntry.setWeight(Integer.parseInt(line.substring(weightMark.length())));
                entries.add(currentEntry);
            }
            else
            {
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(line);
            }
        }
    }

    private boolean questionBegins(String line)
    {
        return line.startsWith(questionMark);
    }
    private boolean weightBegins(String line)
    {
        return line.startsWith(weightMark);
    }
    private boolean answerBegins(String line)
    {
        return line.startsWith(answerMark);
    }
}
