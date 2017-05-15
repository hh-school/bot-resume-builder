package ru.hh.resumebuilderbot.http.type.adapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemsDeserializer<T> implements JsonDeserializer<List<T>> {
    @Override
    public List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonArray jsonItems = json.getAsJsonObject().getAsJsonArray("items");
        List<T> results = new ArrayList<>(jsonItems.size());
        for (JsonElement jsonItem : jsonItems) {
            T item = context.deserialize(
                    jsonItem,
                    ((ParameterizedType) typeOfT).getActualTypeArguments()[0]
            );
            results.add(item);
        }
        return results;
    }
}
