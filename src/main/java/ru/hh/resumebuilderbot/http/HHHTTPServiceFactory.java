package ru.hh.resumebuilderbot.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.hh.resumebuilderbot.Config;
import ru.hh.resumebuilderbot.http.response.entity.Area;
import ru.hh.resumebuilderbot.http.response.entity.Company;
import ru.hh.resumebuilderbot.http.response.entity.Institute;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Skill;
import ru.hh.resumebuilderbot.http.response.entity.Specialization;
import ru.hh.resumebuilderbot.http.type.adapter.CompanyDeserializer;
import ru.hh.resumebuilderbot.http.type.adapter.ItemsDeserializer;

import java.util.List;


public class HHHTTPServiceFactory {
    private static final String HH_BASE_URL = "https://api.hh.ru";

    private static OkHttpClient buildHttpClient() {
        HTTPLoggingInterceptor loggingInterceptor = new HTTPLoggingInterceptor();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("User-Agent", "HH-ResumeBuilderBot/" + Config.VERSION)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });
        return httpClientBuilder.build();
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Company.class, new CompanyDeserializer())

                .registerTypeAdapter(new TypeToken<List<Area>>() {
                }.getType(), new ItemsDeserializer<Area>())
                .registerTypeAdapter(new TypeToken<List<Company>>() {
                }.getType(), new ItemsDeserializer<Company>())
                .registerTypeAdapter(new TypeToken<List<Institute>>() {
                }.getType(), new ItemsDeserializer<Institute>())
                .registerTypeAdapter(new TypeToken<List<Position>>() {
                }.getType(), new ItemsDeserializer<Position>())
                .registerTypeAdapter(new TypeToken<List<Skill>>() {
                }.getType(), new ItemsDeserializer<Skill>())
                .registerTypeAdapter(new TypeToken<List<Specialization>>() {
                }.getType(), new ItemsDeserializer<Specialization>())

                .create();
    }

    public static HHHTTPService get() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .client(buildHttpClient())
                .build();
        return retrofit.create(HHHTTPService.class);
    }
}
