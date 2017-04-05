package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.generator.CurrentQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class StartMessageHandler extends MessageHandler {
    @Override
    public QuestionsGenerator handle(User user, Answer answer) {
        if (UserDataStorage.contains(user)) {
            questionsGenerator.addGenerator(new FixedQuestionGenerator(TextId.ALREADY_STARTED));
        } else {
            UserDataStorage.startNewChat(user);
            questionsGenerator.addGenerator(new FixedQuestionGenerator(TextId.HELLO));
            questionsGenerator.addGenerator(new CurrentQuestionGenerator(user));
        }
        return questionsGenerator;
    }
}
