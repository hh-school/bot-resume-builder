package ru.hh.resumebuilderbot.message.handler;


import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;

public class UnknownMessageHandler extends MessageHandler {
    @Override
    public QuestionsGenerator handle(ChatId chatId, Answer answer) {
        questionsGenerator.addGenerator(new FixedQuestionGenerator(TextId.UNKNOWN));
        return questionsGenerator;
    }
}
