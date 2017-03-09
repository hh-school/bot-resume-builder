package ru.hh.resumebuilderbot;

class ChatId {
    // todo: make private and override constructor
    final long index;

    ChatId(long index) {
        this.index = index;
    }
}

enum AnswerType {
    YesNo,
    AnyString,
    SelectedFromList
}

class Question {
    private final ChatId chatId;
    private final String text;
//    private ru.hh.resumebuilderbot.AnswerType answerType;
//    private List<String> allowedAnswers;

    public Question(ChatId chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public ChatId getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }
}

class Answer {
    private ChatId chatId;
    private Object answerBody;

    public Answer(ChatId chatId, Object answerBody) {
        this.chatId = chatId;
        this.answerBody = answerBody;
    }
}

interface MessengerAdapter {
    // Задать вопрос пользователю
    void ask(Question question, int timeoutMs);

    void setListener(AbstractBotBody bot);

    // Начать принимать сообщения от пользователя
    void start();
}

interface AbstractBotBody {
    void onAnswer(Answer answer, int timeoutMs);
    void onStartChat(ChatId chatId);
    void start();
}

abstract class AuthData {}

abstract class CV {}

interface JobSiteAdapter
{
    void connect(AuthData authData, int timeout);
    void register(AuthData authData, int timeout);
    void pushCV(CV cv);
}
