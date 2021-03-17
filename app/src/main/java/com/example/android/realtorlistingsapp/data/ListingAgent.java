package com.example.android.realtorlistingsapp.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class ListingAgent implements Serializable {

    public String id;
    public String name;

    public ListingAgent(String id, String name){
        this.id = id;
        this.name = name;
    }

    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<ListingAgent> {
        @Override
        public ListingAgent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject agentObj = json.getAsJsonObject();
            return new ListingAgent(
                    agentObj.getAsJsonPrimitive("id").getAsString(),
                    agentObj.getAsJsonPrimitive("name").getAsString()
            );
        }
    }
}
