package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class ClearMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        questionsQueue.add(new FixedQuestionGenerator(TextId.CLEARED));
        questionsQueue.add(new FirstQuestionGenerator());
        return questionsQueue;
    }
}
