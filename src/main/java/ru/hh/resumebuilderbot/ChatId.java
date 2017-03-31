package ru.hh.resumebuilderbot;

public class ChatId {
    // todo: make private and override constructor
    private final long index;

    public ChatId(long index) {
        this.index = index;
    }

    public long getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatId chatId = (ChatId) o;

        return getIndex() == chatId.getIndex();
    }

    @Override
    public int hashCode() {
        return (int) (getIndex() ^ (getIndex() >>> 32));
    }
}
