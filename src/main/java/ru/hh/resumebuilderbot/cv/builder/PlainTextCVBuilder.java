package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.User;

public class PlainTextCVBuilder implements CVBuilder {
    private UserDataStorage userDataStorage;

    public PlainTextCVBuilder(UserDataStorage userDataStorage) {
        this.userDataStorage = userDataStorage;
    }

    @Override
    public String build(User user) {

        return "not implemented yet";
    }
}
