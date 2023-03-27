package io.github.thongtpvinh3.helper;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

public class GsonHelper {

    private static GsonHelper instance;

    private final Gson gson = new Gson();

    public static GsonHelper getInstance() {
        if (instance == null) {
            instance = new GsonHelper();
        }
        return instance;
    }

    public <T> String convertFromObject(T t) {
        return gson.toJson(t);
    }

    public <T> T convertToObject(String json, Class<T> classType) {
        try {
            return gson.fromJson(json, classType);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public <T> T convertToObject(JsonElement jsonElement, Type classType) {
        return gson.fromJson(jsonElement, classType);
    }
}
