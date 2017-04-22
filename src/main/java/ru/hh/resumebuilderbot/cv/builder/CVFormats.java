package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public enum CVFormats {
    PLAIN_TEXT {
        @Override
        public CVBuilder getBuilder(UserDataStorage userDataStorage) {
            return new PlainTextCVBuilder(userDataStorage);
        }
    };

    public abstract CVBuilder getBuilder(UserDataStorage userDataStorage);
}
