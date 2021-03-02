package com.example.android.githubsearchwithsqlite.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BookmarkedReposDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GitHubRepo repo);

    @Delete
    void delete(GitHubRepo repo);

    @Query("SELECT * FROM bookmarkedRepos")
    LiveData<List<GitHubRepo>> getAllRepos();

    @Query("SELECT * FROM bookmarkedRepos WHERE fullName = :fullName LIMIT 1 ")
    LiveData<GitHubRepo> getRepoByName(String fullName);
}
