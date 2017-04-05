package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;

import java.util.Queue;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class BotBodyImpl implements BotBody {
    private MessengerAdapter messengerAdapter;

    @Override
    public void answer(User user, Answer answer) {
		synchronized (UserDataStorage.getMutex(chatId))
        {
			MessageHandler messageHandler = Selector.select(answer);
			QuestionsGenerator questionGeneratorQueue = messageHandler.handle(user, answer);
			Queue<Question> questions = questionGeneratorQueue.generateQuestions(user);
			questions.forEach((x) -> messengerAdapter.ask(user, x));
		}
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
