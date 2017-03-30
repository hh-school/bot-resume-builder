package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;

public class FirstQuestionGenerator implements QuestionGenerator {


    private final QuestionGenerator questionGenerator;

    public FirstQuestionGenerator() {
        this.questionGenerator = new QuestionGeneratorByNumber(0);
    }

    @Override
    public Question generateNext(ChatId chatId) {
        return questionGenerator.generateNext(chatId);
    }

    @Override
    public void setPrefix(String prefix) {
        questionGenerator.setPrefix(prefix);
    }
}
