package com.example.android.realtorlistingsapp.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BookmarkedReposRepository {
    private BookmarkedReposDao dao;

    public BookmarkedReposRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.dao = db.bookmarkedReposDao();
    }

    public void insertBookmarkedRepo(GitHubRepo repo) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(repo);
            }
        });
    }

    public void deleteBookmarkedRepo(GitHubRepo repo) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(repo);
            }
        });
    }

    public LiveData<List<GitHubRepo>> getAllBookmarkedRepos() {
        return this.dao.getAllRepos();
    }

    public LiveData<GitHubRepo> getBookmarkedRepoByName(String fullName) {
        return this.dao.getRepoByName(fullName);
    }
}
