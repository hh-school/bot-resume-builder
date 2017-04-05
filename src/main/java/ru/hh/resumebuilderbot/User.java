package ru.hh.resumebuilderbot;

public class User {
    // todo: make private and override constructor
    private final long index;

    public User(long index) {
        this.index = index;
    }

    public long getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return getIndex() == user.getIndex();
    }

    @Override
    public int hashCode() {
        return (int) (getIndex() ^ (getIndex() >>> 32));
    }
}
