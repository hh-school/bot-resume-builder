package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.*;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorByNumber;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorsQueue;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;
import ru.hh.resumebuilderbot.questions.storage.QuestionsStorage;

public class AnswerMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(ChatId chatId, Answer answer) {
        UserDataStorage.registerAnswer(chatId, answer);

        if (QuestionsStorage.finished(chatId)) {
            questionGeneratorsQueue.add(new FixedQuestionGenerator(TextId.FINISHED));
            return questionGeneratorsQueue;
        }

        questionGeneratorsQueue.add(new QuestionGeneratorByNumber(currentQuestionNumber));
        return questionGeneratorsQueue;
    }
}
