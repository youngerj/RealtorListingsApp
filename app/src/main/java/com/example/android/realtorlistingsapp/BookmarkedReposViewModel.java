package com.example.android.realtorlistingsapp;

import android.app.Application;

import com.example.android.realtorlistingsapp.data.BookmarkedReposRepository;
import com.example.android.realtorlistingsapp.data.RealtorListing;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BookmarkedReposViewModel extends AndroidViewModel {
    private BookmarkedReposRepository repository;

    public BookmarkedReposViewModel(Application application) {
        super(application);
        this.repository = new BookmarkedReposRepository(application);
    }

    public void insertBookmarkedRepo(RealtorListing repo) {
        this.repository.insertBookmarkedRepo(repo);
    }

    public void deleteBookmarkedRepo(RealtorListing repo) {
        this.repository.deleteBookmarkedRepo(repo);
    }

    public LiveData<List<RealtorListing>> getAllBookmarkedRepos() {
        return this.repository.getAllBookmarkedRepos();
    }

    public LiveData <RealtorListing> getBookmarkedRepoByName(String fullName) {
        return this.repository.getBookmarkedRepoByName(fullName);
    }
}
