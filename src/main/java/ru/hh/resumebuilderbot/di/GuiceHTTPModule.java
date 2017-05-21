package ru.hh.resumebuilderbot.di;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import okhttp3.OkHttpClient;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.http.HHHTTPServiceUtils;

public class GuiceHTTPModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return HHHTTPServiceUtils.buildHttpClient();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return HHHTTPServiceUtils.buildGson();
    }

    @Provides
    @Singleton
    public HHHTTPService provideHHHTTPService(Gson gson, OkHttpClient okHttpClient) {
        return HHHTTPServiceUtils.getService(gson, okHttpClient);
    }
}
