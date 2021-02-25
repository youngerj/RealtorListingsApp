package com.example.android.githubsearchwithsqlite.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarkedRepos")
public class GitHubRepo implements Serializable {
    @SerializedName("full_name")
    @PrimaryKey
    @NonNull
    public String fullName;

    public String description;

    @SerializedName("html_url")
    @NonNull
    public String htmlUrl;

    @SerializedName("stargazers_count")
    public int stars;
}
