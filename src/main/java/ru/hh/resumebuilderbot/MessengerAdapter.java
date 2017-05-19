package ru.hh.resumebuilderbot;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import ru.hh.resumebuilderbot.question.Question;

import java.util.List;

public interface MessengerAdapter {
    void ask(Long telegramId, Question question);

    void start();

    void provideSuggests(String queryId, List<InlineQueryResult> inlineQueryResults);

    void editMessage(MessageUpdate messageUpdate);
}
