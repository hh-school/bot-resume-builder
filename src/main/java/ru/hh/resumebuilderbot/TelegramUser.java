package ru.hh.resumebuilderbot;

public class TelegramUser {
    // todo: make private and override constructor
    private final long id;

    public TelegramUser(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TelegramUser telegramUser = (TelegramUser) o;

        return getId() == telegramUser.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
