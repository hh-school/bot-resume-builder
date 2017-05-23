package ru.hh.resumebuilderbot.http.type.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Specialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PositionDeserializer implements JsonDeserializer<Position> {
    @Override
    public Position deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String name = jsonObject.get("text").getAsString();
        String id = jsonObject.get("id").getAsString();
        List<Specialization> specializations = new ArrayList<>();

        for (JsonElement jsonSpecialization : jsonObject.get("specializations").getAsJsonArray()) {
            Specialization specialization = context.deserialize(jsonSpecialization, Specialization.class);
            specializations.add(specialization);
        }

        return new Position(id, name, specializations);
    }
}
