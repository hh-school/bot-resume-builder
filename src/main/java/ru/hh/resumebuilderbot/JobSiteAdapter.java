package ru.hh.resumebuilderbot;

interface JobSiteAdapter {
    void connect(AuthData authData, int timeout);
    void register(AuthData authData, int timeout);
    void pushCV(CV cv);
}
