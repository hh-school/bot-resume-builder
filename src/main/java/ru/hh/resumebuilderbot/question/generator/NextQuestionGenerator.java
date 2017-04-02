package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.questions.storage.QuestionsStorage;

public class NextQuestionGenerator implements QuestionGenerator {

    private final ChatId chatId;

    public NextQuestionGenerator(ChatId chatId) {
        this.chatId = chatId;
    }

    @Override
    public Question generateNext() {
        return QuestionsStorage.getNextQuestion(chatId);
    }
}
