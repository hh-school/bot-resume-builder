package ru.hh.resumebuilderbot.queryresults.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Sergey on 20.04.2017.
 */
public class SuggestGenerator {
    protected final static Logger log = LoggerFactory.getLogger(URLRequest.class);

    private final static String instUrlRequest = "https://api.hh.ru/suggests/educational_institutions";

    private final static String CompaniesUrlRequest = "https://api.hh.ru/suggests/companies";


    public static List<Map<String, String>> getInstitutes(String searchQuery) {
        List<Map<String, String>> results = new ArrayList<>();
        if (searchQuery.length() < 2) {
            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("text", "Не выбран ВУЗ");
            errorResult.put("description", "Продолжайте ввод названия ВУЗа");
            errorResult.put("title", "Для появления подсказки требуется минимум 2 введенных символа");
            results.add(errorResult);
            return results;
        }
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("text", searchQuery);
        queryParams.put("locale", getQueryLanguage(searchQuery));

        String jsonInput = URLRequest.get(instUrlRequest, queryParams);
        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(jsonInput).getAsJsonObject();
        JsonArray institutes = mainObject.getAsJsonArray("items");
        for (JsonElement institute : institutes) {
            JsonObject instituteObject = institute.getAsJsonObject();
            Map<String, String> instResult = new HashMap<>();

            String acronym = instituteObject.get("acronym").getAsString();
            String text = instituteObject.get("text").getAsString();
            String id = instituteObject.get("id").getAsString();

            instResult.put("id", id);
            instResult.put("text", text);
            if (acronym.equals("")) {
                instResult.put("title", text);
            } else {
                instResult.put("title", acronym);
                instResult.put("description", text);
            }
            results.add(instResult);
        }
        return results;
    }

    private static String getQueryLanguage(String query) {
        //пока для простоты 2 языка
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁ]*");
        if (pattern.matcher(query).matches()) {
            return "RU";
        }
        return "EN";
    }
}
