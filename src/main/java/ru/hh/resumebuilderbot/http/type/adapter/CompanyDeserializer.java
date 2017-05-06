package ru.hh.resumebuilderbot.http.type.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ru.hh.resumebuilderbot.http.response.entity.Company;

import java.lang.reflect.Type;

public class CompanyDeserializer implements JsonDeserializer<Company> {
    @Override
    public Company deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String url = "";
        if (!jsonObject.get("url").isJsonNull()) {
            url = jsonObject.get("url").getAsString();
        }
        String name = jsonObject.get("text").getAsString();
        String id = jsonObject.get("id").getAsString();

        String logoUrl = "";
        if (!jsonObject.get("logo_urls").isJsonNull()) {
            JsonObject companyLogo = jsonObject.get("logo_urls").getAsJsonObject();
            logoUrl = companyLogo.get("90").getAsString();
        }
        return new Company(id, url, name, logoUrl);
    }
}
