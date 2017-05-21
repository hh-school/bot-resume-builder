package ru.hh.resumebuilderbot.http;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HHHTTPServiceTestUtils {
    public static HHHTTPService getService(Gson gson, OkHttpClient okHttpClient, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(HHHTTPService.class);
    }
}
