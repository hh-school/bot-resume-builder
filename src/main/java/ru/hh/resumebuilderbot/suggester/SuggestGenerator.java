package ru.hh.resumebuilderbot.suggester;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SuggestGenerator {
    protected final static Logger log = LoggerFactory.getLogger(SuggestGenerator.class);

    private final static String institutesUrl = "https://api.hh.ru/suggests/educational_institutions";

    private final static String facultiesUrl = "https://api.hh.ru/educational_institutions/%s/faculties";

    private final static String companiesUrl = "https://api.hh.ru/suggests/companies";

    private final static String specializationsUrl = "https://api.hh.ru/suggests/fields_of_study";

    private final static String skillsUrl = "https://api.hh.ru/suggests/skill_set";

    private final static String positionsUrl = "https://api.hh.ru/suggests/positions";

    private final static String areasUrl = "https://api.hh.ru/suggests/areas";

    public static RawSuggestData getInstitutes(String searchQuery) {
        if (searchQuery.length() < 2) {
            return shortQueryResult();
        }

        JsonArray institutes = getJsonArrayFromURL(institutesUrl, searchQuery);
        if (institutes.size() == 0) {
            return nothingFoundResult(searchQuery);
        }

        RawSuggestData results = new RawSuggestData();

        for (JsonElement institute : institutes) {
            JsonObject instituteObject = institute.getAsJsonObject();
            Map<String, String> instResult = new HashMap<>();

            String acronym = "";
            if (!instituteObject.get("acronym").isJsonNull()) {
                acronym = instituteObject.get("acronym").getAsString();
            }
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

    public static RawSuggestData getFaculties(String instId, String searchQuery) {
        JsonArray faculties = getJsonArrayFromURL(String.format(facultiesUrl, instId));

        if (faculties.size() == 0) {
            return instituteHasNoFaculties();
        }

        RawSuggestData results = new RawSuggestData();

        for (JsonElement faculty : faculties) {
            JsonObject facultyObject = faculty.getAsJsonObject();

            String text = facultyObject.get("name").getAsString();
            if (!text.toLowerCase().contains(searchQuery.toLowerCase())) {
                continue;
            }

            String id = facultyObject.get("id").getAsString();

            Map<String, String> instResult = new HashMap<>();
            instResult.put("id", id);
            instResult.put("text", text);
            instResult.put("title", text);

            results.add(instResult);
        }
        if (results.size() == 0) {
            return nothingFoundResult(searchQuery);
        }
        return results;
    }

    public static RawSuggestData getCompanies(String searchQuery) {
        if (searchQuery.length() < 2) {
            return shortQueryResult();
        }

        JsonArray companies = getJsonArrayFromURL(companiesUrl, searchQuery);

        if (companies.size() == 0) {
            return nothingFoundResult(searchQuery);
        }
        RawSuggestData results = new RawSuggestData();
        for (JsonElement company : companies) {
            JsonObject companyObject = company.getAsJsonObject();
            Map<String, String> companyResult = new HashMap<>();

            String url = "";
            if (!companyObject.get("url").isJsonNull()) {
                url = companyObject.get("url").getAsString();
            }
            String text = companyObject.get("text").getAsString();
            String id = companyObject.get("id").getAsString();

            String thumb = "";
            if (!companyObject.get("logo_urls").isJsonNull()) {
                JsonObject companyLogo = companyObject.get("logo_urls").getAsJsonObject();
                thumb = companyLogo.get("90").getAsString();
            }

            companyResult.put("id", id);
            companyResult.put("text", text);
            if (!thumb.equals("")) {
                companyResult.put("thumb", thumb);
            }
            companyResult.put("title", text);
            companyResult.put("description", url);
            results.add(companyResult);
        }
        return results;
    }

    public static RawSuggestData getSpecializations(String searchQuery) {
        return standartQueryResult(specializationsUrl, searchQuery);
    }

    public static RawSuggestData getSkills(String searchQuery) {
        return standartQueryResult(skillsUrl, searchQuery);
    }

    public static RawSuggestData getPositions(String searchQuery) {
        return standartQueryResult(positionsUrl, searchQuery);
    }

    public static RawSuggestData getAreas(String searchQuery) {
        return standartQueryResult(areasUrl, searchQuery);
    }

    private static String getQueryLanguage(String query) {
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁ]*");
        if (pattern.matcher(query).matches()) {
            return "RU";
        }
        return "EN";
    }

    private static RawSuggestData shortQueryResult() {
        Map<String, String> errorResult = new HashMap<>();
        errorResult.put("text", TextsStorage.getText(TextId.NOTHING_SELECTED));
        errorResult.put("description", TextsStorage.getText(TextId.NEED_MORE_ONE_SYMBOL));
        errorResult.put("title", TextsStorage.getText(TextId.CONTINUE_INPUT));
        return new RawSuggestData(errorResult);
    }

    private static RawSuggestData nothingFoundResult(String query) {
        Map<String, String> errorResult = new HashMap<>();
        errorResult.put("text", TextsStorage.getText(TextId.NOTHING_SELECTED));
        errorResult.put("description", TextsStorage.getText(TextId.CHECK_INPUT_DATA));
        errorResult.put("title", String.format("По запросу '%s' ничего не найдено", query));
        return new RawSuggestData(errorResult);
    }

    private static RawSuggestData instituteHasNoFaculties() {
        Map<String, String> errorResult = new HashMap<>();
        errorResult.put("text", TextsStorage.getText(TextId.NO_FACULTIES_FOUND));
        errorResult.put("description", TextsStorage.getText(TextId.NO_FACULTIES_FOUND_DESCRIPTION));
        errorResult.put("title", TextsStorage.getText(TextId.NO_FACULTIES_FOUND));
        return new RawSuggestData(errorResult);
    }

    private static RawSuggestData standartQueryResult(String url, String searchQuery) {
        if (searchQuery.length() < 2) {
            return shortQueryResult();
        }
        JsonArray array = getJsonArrayFromURL(url, searchQuery);

        if (array.size() == 0) {
            return nothingFoundResult(searchQuery);
        }
        RawSuggestData results = new RawSuggestData();
        for (JsonElement element : array) {
            JsonObject elementObject = element.getAsJsonObject();
            Map<String, String> companyResult = new HashMap<>();

            String text = elementObject.get("text").getAsString();
            String id = elementObject.get("id").getAsString();

            companyResult.put("id", id);
            companyResult.put("text", text);
            companyResult.put("title", text);
            results.add(companyResult);
        }
        return results;
    }

    private static JsonArray getJsonArrayFromURL(String url, String query) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("text", query);
        queryParams.put("locale", getQueryLanguage(query));

        String jsonInput = HTTPClient.sendGetRequest(url, queryParams);
        JsonParser parser = new JsonParser();
        return parser.parse(jsonInput).getAsJsonObject().getAsJsonArray("items");
    }

    private static JsonArray getJsonArrayFromURL(String url) {
        String jsonInput = HTTPClient.sendGetRequest(url);
        JsonParser parser = new JsonParser();
        return parser.parse(jsonInput).getAsJsonArray();
    }
}
