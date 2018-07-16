package com.example.cjcucsie.okhttp;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Convert implements JsonDeserializer {
    @Override
    public Weather deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject=json.getAsJsonObject();
        JsonObject main=jsonObject.getAsJsonObject("main");
        String pressure=main.get("pressure").getAsString();
        String temp=main.get("temp").getAsString();
        return new Weather(pressure,temp);
    }
}
