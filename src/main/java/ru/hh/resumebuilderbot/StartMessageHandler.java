package ru.hh.resumebuilderbot;

public class StartMessageHandler implements MessageHandler {
    @Override
    public NextQuestionGenerator handle(Answer answer) {
        ChatId chatId = answer.getChatId();
        if (UserDataStorage.contains(chatId))
        {
            return new FixedQuestionGenerator(TextsStorage.getText("AlreadyStarted"));
        }
        else
        {
            return new QuestionGeneratorByNumber(0);
        }
    }
}
