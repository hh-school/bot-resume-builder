package ru.hh.resumebuilderbot;

public class ChatId {
    public long getIndex() {
        return index;
    }

    // todo: make private and override constructor
    final long index;

    public ChatId(long index) {
        this.index = index;
    }
}
