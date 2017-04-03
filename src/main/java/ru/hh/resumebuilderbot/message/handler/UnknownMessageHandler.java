package ru.hh.resumebuilderbot.message.handler;


import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;

public class UnknownMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(Answer answer) {
        questionsQueue.add(new FixedQuestionGenerator(TextId.UNKNOWN));
        return questionsQueue;
    }
}
