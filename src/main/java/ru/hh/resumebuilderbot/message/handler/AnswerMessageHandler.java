package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.CurrentQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class AnswerMessageHandler extends MessageHandler {
    @Override
    public QuestionsGenerator handle(ChatId chatId, Answer answer) {
        if (UserDataStorage.finished(chatId)) {
            questionsGenerator.addGenerator(new FixedQuestionGenerator(TextId.FINISHED));
            return questionsGenerator;
        }

        UserDataStorage.registerAnswer(chatId, answer);

        if (UserDataStorage.finished(chatId)) {
            questionsGenerator.addGenerator(new FixedQuestionGenerator(TextId.FINISHED));
            return questionsGenerator;
        }

        questionsGenerator.addGenerator(new CurrentQuestionGenerator(chatId));
        return questionsGenerator;
    }
}
