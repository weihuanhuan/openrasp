package com.baidu.openrasp.config.js.setter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ValueSetterFactory {

    public static ValueSetter getValueSetter(JsonElement jsonElement) {
        if (jsonElement == null) {
            return new DummyValueSetter();
        } else if (jsonElement.isJsonNull()) {
            JsonNull asJsonNull = jsonElement.getAsJsonNull();
            return getValueSetter(asJsonNull);
        } else if (jsonElement.isJsonArray()) {
            JsonArray asJsonArray = jsonElement.getAsJsonArray();
            return getValueSetter(asJsonArray);
        } else if (jsonElement.isJsonObject()) {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return getValueSetter(asJsonObject);
        } else if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
            return getValueSetter(asJsonPrimitive);
        }
        return new DummyValueSetter();
    }

    private static ValueSetter getValueSetter(JsonNull jsonNull) {
        return new DummyValueSetter();
    }

    private static ValueSetter getValueSetter(JsonArray jsonArray) {
        return new DummyValueSetter();
    }

    private static ValueSetter getValueSetter(JsonObject jsonObject) {
        return new ObjectValueSetter();
    }

    private static ValueSetter getValueSetter(JsonPrimitive jsonPrimitive) {
        if (jsonPrimitive.isBoolean()) {
            return new BooleanValueSetter();
        } else if (jsonPrimitive.isString()) {
            return new StringValueSetter();
        } else if (jsonPrimitive.isNumber()) {
            return new NumberValueSetter();
        } else {
            return new DummyValueSetter();
        }
    }

}
