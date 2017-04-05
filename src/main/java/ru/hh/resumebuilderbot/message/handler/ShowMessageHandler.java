package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;
import ru.hh.resumebuilderbot.question.generator.ShowAllQuestionGenerator;

public class ShowMessageHandler extends MessageHandler {
    @Override
    public QuestionsGenerator handle(ChatId chatId, Answer answer) {
        questionsGenerator.addGenerator(new ShowAllQuestionGenerator(chatId));
        return questionsGenerator;
    }
}
