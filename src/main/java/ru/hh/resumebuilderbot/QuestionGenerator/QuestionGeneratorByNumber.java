package ru.hh.resumebuilderbot.QuestionGenerator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.QuestionsStorage;

public class QuestionGeneratorByNumber implements QuestionGenerator {

    private final int questionNumber;

    public QuestionGeneratorByNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public Question generate(ChatId chatId) {
        return new Question(chatId, QuestionsStorage.getQuestion(questionNumber));
    }
}
