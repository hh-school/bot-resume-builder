package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.TextsStorage;

public class FixedQuestionGenerator implements QuestionGenerator {

    private final String text;

    public FixedQuestionGenerator(TextsStorage.TextId id) {

        this.text = TextsStorage.getText(id);
    }

    @Override
    public Question generateNext(ChatId chatId) {
        return new Question(chatId, text);
    }
}
