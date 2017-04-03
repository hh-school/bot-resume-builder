package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.NextQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorsQueue;
import ru.hh.resumebuilderbot.question.storage.QuestionsStorage;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class AnswerMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(ChatId chatId, Answer answer) {
        if (QuestionsStorage.finished(chatId)) {
            questionGeneratorsQueue.add(new FixedQuestionGenerator(TextId.FINISHED));
            return questionGeneratorsQueue;
        }

        UserDataStorage.registerAnswer(chatId, answer);

        if (QuestionsStorage.finished(chatId)) {
            questionGeneratorsQueue.add(new FixedQuestionGenerator(TextId.FINISHED));
            return questionGeneratorsQueue;
        }

        questionGeneratorsQueue.add(new NextQuestionGenerator(chatId));
        return questionGeneratorsQueue;
    }
}
