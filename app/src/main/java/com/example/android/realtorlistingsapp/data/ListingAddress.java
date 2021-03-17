package com.example.android.realtorlistingsapp.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class ListingAddress implements Serializable {
    String line;

    public ListingAddress(String line){
        this.line = line;
    }

    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<ListingAddress> {
        @Override
        public ListingAddress deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject addrObj = json.getAsJsonObject();
            return new ListingAddress(
                    addrObj.getAsJsonPrimitive("line").getAsString()
            );
        }
    }
}
