package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorsQueue;
import ru.hh.resumebuilderbot.question.generator.ShowAllQuestionGenerator;

public class ShowMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(ChatId chatId, Answer answer) {
        questionGeneratorsQueue.add(new ShowAllQuestionGenerator(chatId));
        return questionGeneratorsQueue;
    }
}
