package ru.hh.resumebuilderbot.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.hh.resumebuilderbot.Config;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.http.response.entity.Area;
import ru.hh.resumebuilderbot.http.response.entity.Company;
import ru.hh.resumebuilderbot.http.response.entity.Institute;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Skill;
import ru.hh.resumebuilderbot.http.response.entity.StudyField;
import ru.hh.resumebuilderbot.http.response.entity.Vacancy;
import ru.hh.resumebuilderbot.http.type.adapter.CompanyDeserializer;
import ru.hh.resumebuilderbot.http.type.adapter.ItemsDeserializer;
import ru.hh.resumebuilderbot.http.type.adapter.PositionDeserializer;
import ru.hh.resumebuilderbot.http.type.adapter.UserSerializer;
import ru.hh.resumebuilderbot.http.type.adapter.VacancyDeserializer;

import java.util.List;


public class HHHTTPServiceUtils {
    private static final String HH_BASE_URL = "https://api.hh.ru";

    public static OkHttpClient buildHttpClient() {
        HTTPLoggingInterceptor loggingInterceptor = new HTTPLoggingInterceptor();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("User-Agent", "HH-ResumeBuilderBot/" + Config.VERSION)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                });
        return httpClientBuilder.build();
    }

    public static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Company.class, new CompanyDeserializer())
                .registerTypeAdapter(Position.class, new PositionDeserializer())
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Vacancy.class, new VacancyDeserializer())

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
                .registerTypeAdapter(new TypeToken<List<StudyField>>() {
                }.getType(), new ItemsDeserializer<StudyField>())
                .registerTypeAdapter(new TypeToken<List<Vacancy>>() {
                }.getType(), new ItemsDeserializer<Vacancy>())

                .create();
    }

    public static HHHTTPService getService(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(HHHTTPService.class);
    }
}
