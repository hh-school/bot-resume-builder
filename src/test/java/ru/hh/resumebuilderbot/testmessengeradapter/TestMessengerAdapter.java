package ru.hh.resumebuilderbot.testmessengeradapter;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.BotBody;
import ru.hh.resumebuilderbot.MessageUpdate;
import ru.hh.resumebuilderbot.MessengerAdapter;
import ru.hh.resumebuilderbot.question.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestMessengerAdapter implements MessengerAdapter {

    private final String defaultAnswerText = "cant understand your question";

    private BotBody bot;

    private List<Entry> entries = new ArrayList<>();

    private String filename;

    public TestMessengerAdapter(String filename) {
        this.filename = filename;
    }

    @Override
    public void ask(Long telegramId, Question question) {
        bot.askNextQuestions(telegramId, getAnswer(question));
    }


    @Override
    public void start() {
        try {
            load(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void provideSuggests(String queryId, List<InlineQueryResult> inlineQueryResults) {
    }

    @Override
    public void editMessage(MessageUpdate messageUpdate) {
    }

    private Answer getAnswer(Question question) {
        List<Entry> candidates = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getPattern().matcher(question.getText()).matches()) {
                candidates.add(entry);
            }
        }
        return new Answer(getWeightedRandomAnswer(candidates));
    }

    private String getWeightedRandomAnswer(List<Entry> candidates) {
        if (candidates.isEmpty()) {
            return defaultAnswerText;
        }
        int maxRandomValue = 0;
        for (Entry entry : candidates) {
            maxRandomValue += entry.getWeight();
        }
        Random rng = new Random();
        int randomValue = rng.nextInt(maxRandomValue);
        int counter = 0;
        for (Entry entry : candidates) {
            counter += entry.getWeight();
            if (counter > randomValue) {
                return entry.getAnswer();
            }
        }
        return defaultAnswerText;
    }

    private void load(String filename) throws IOException {
        new Loader(entries).load(filename);
    }
}
