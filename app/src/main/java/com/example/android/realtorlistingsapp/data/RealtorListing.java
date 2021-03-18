package com.example.android.realtorlistingsapp.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "realtorListings")
public class RealtorListing implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("listing_id")
    public String id;

    @SerializedName("rdc_web_url")
    public String url;

    @SerializedName("price")
    public int price;

    @SerializedName("beds")
    public int beds;

    @SerializedName("baths")
    public int baths;

    @SerializedName("thumbnail")
    public String img;

    @SerializedName("agent_id")
    public String agentId;

    @Ignore
    @SerializedName("address")
    public ListingAddress address;

    @Ignore
    @SerializedName("agents")
    public ArrayList<ListingAgent> agents;

    public String line;
    public String l_agent_id;
    public String l_agent_name;
}
