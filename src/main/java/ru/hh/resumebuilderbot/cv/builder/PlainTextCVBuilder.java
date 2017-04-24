package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

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
