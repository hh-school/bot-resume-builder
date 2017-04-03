package ru.hh.resumebuilderbot.telegram.adapter.answer;

public abstract class ProtoTelegramAnswer implements TelegramAnswer {

    protected String answerText;
    protected long innerChatId;

    @Override
    public String getAnswerText() {
        return answerText;
    }

    @Override
    public long getInnerChatId() {
        return innerChatId;
    }
}
