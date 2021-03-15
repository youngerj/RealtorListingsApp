package com.example.android.realtorlistingsapp;

import android.app.Application;

import com.example.android.realtorlistingsapp.data.BookmarkedReposRepository;
import com.example.android.realtorlistingsapp.data.GitHubRepo;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BookmarkedReposViewModel extends AndroidViewModel {
    private BookmarkedReposRepository repository;

    public BookmarkedReposViewModel(Application application) {
        super(application);
        this.repository = new BookmarkedReposRepository(application);
    }

    public void insertBookmarkedRepo(GitHubRepo repo) {
        this.repository.insertBookmarkedRepo(repo);
    }

    public void deleteBookmarkedRepo(GitHubRepo repo) {
        this.repository.deleteBookmarkedRepo(repo);
    }

    public LiveData<List<GitHubRepo>> getAllBookmarkedRepos() {
        return this.repository.getAllBookmarkedRepos();
    }

    public LiveData <GitHubRepo> getBookmarkedRepoByName(String fullName) {
        return this.repository.getBookmarkedRepoByName(fullName);
    }
}
