package ru.hh.resumebuilderbot;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// Заглушка для адаптера к серверу hh.ru
public class HHAdapter implements JobSiteAdapter {
    @Override
    public void connect(AuthData authData, int timeout) {
        throw new NotImplementedException();
    }

    @Override
    public void register(AuthData authData, int timeout) {
        throw new NotImplementedException();
    }

    @Override
    public void pushCV(CV cv) {
        throw new NotImplementedException();
    }
}
