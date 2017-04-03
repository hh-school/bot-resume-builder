package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.QuestionsStorage;

public class NextQuestionGenerator implements QuestionGenerator {

    private final ChatId chatId;

    public NextQuestionGenerator(ChatId chatId) {
        this.chatId = chatId;
    }

    @Override
    public Question generate() {
        return QuestionsStorage.getNextQuestion(chatId);
    }
}
