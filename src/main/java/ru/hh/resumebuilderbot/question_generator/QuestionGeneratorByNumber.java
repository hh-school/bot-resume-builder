package ru.hh.resumebuilderbot.question_generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.QuestionsStorage;

public class QuestionGeneratorByNumber implements QuestionGenerator {

    private final int questionNumber;

    public QuestionGeneratorByNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public Question generateNext(ChatId chatId) {
        return new Question(chatId, QuestionsStorage.getQuestion(questionNumber));
    }
}
