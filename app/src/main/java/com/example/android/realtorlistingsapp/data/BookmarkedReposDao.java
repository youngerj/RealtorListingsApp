package com.example.android.realtorlistingsapp.data;

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
    void insert(RealtorListing repo);

    @Delete
    void delete(RealtorListing repo);

    @Query("SELECT * FROM realtorListings")
    LiveData<List<RealtorListing>> getAllRepos();

    @Query("SELECT * FROM realtorListings WHERE id = :fullName LIMIT 1 ")
    LiveData<RealtorListing> getRepoByName(String fullName);
}
