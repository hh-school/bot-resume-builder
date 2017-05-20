package ru.hh.resumebuilderbot.http.type.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ru.hh.resumebuilderbot.http.response.entity.Vacancy;

import java.lang.reflect.Type;

public class VacancyDeserializer implements JsonDeserializer<Vacancy> {
    @Override
    public Vacancy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Vacancy vacancy = new Vacancy();
        JsonObject jsonObject = json.getAsJsonObject();
        vacancy.setId(jsonObject.get("id").getAsString());
        JsonElement salaryJsonElement = jsonObject.get("salary");
        if (!salaryJsonElement.isJsonNull()) {
            JsonObject salaryJson = salaryJsonElement.getAsJsonObject();
            JsonElement salaryFromJsonElement = salaryJson.get("from");
            if (!salaryFromJsonElement.isJsonNull()) {
                vacancy.setSalaryFrom(salaryFromJsonElement.getAsInt());
            }
            JsonElement salaryToJsonElement = salaryJson.get("to");
            if (!salaryToJsonElement.isJsonNull()) {
                vacancy.setSalaryTo(salaryToJsonElement.getAsInt());
            }
            JsonElement salaryCurrencyJsonElement = salaryJson.get("currency");
            if (!salaryCurrencyJsonElement.isJsonNull()) {
                vacancy.setSalaryCurrency(salaryCurrencyJsonElement.getAsString());
            }
        }
        vacancy.setName(jsonObject.get("name").getAsString());
        vacancy.setUrl(jsonObject.get("alternate_url").getAsString());

        JsonObject employerJson = jsonObject.get("employer").getAsJsonObject();
        String companyName = employerJson.get("name").getAsString();
        JsonElement companyLogoUrlsJsonElement = employerJson.get("logo_urls");
        if (companyLogoUrlsJsonElement != null && !companyLogoUrlsJsonElement.isJsonNull()) {
            String companyLogo = companyLogoUrlsJsonElement.getAsJsonObject().get("original").getAsString();
            vacancy.setCompanyLogo(companyLogo);
        }
        vacancy.setCompanyName(companyName);
        return vacancy;
    }
}
