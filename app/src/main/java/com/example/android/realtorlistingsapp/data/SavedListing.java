package com.example.android.realtorlistingsapp.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SavedListing implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("listingId")
    public String id;

    @SerializedName("listingUrl")
    public String url;

    @SerializedName("listingPrice")
    public int price;

    @SerializedName("listingBeds")
    public int beds;

    @SerializedName("listingBaths")
    public int baths;

    @SerializedName("listingImg")
    public String img;

    @SerializedName("listingLine")
    public String line;

    @SerializedName("listingAgentId")
    public String agentId;
}
