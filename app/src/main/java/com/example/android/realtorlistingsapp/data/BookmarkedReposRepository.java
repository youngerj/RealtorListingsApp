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

    public void insertBookmarkedRepo(RealtorListing repo) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                repo.line = repo.address.line;
                repo.l_agent_id = repo.agents.get(0).id;
                repo.l_agent_name = repo.agents.get(0).name;
                dao.insert(repo);
            }
        });
    }

    public void deleteBookmarkedRepo(RealtorListing repo) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(repo);
            }
        });
    }

    public LiveData<List<RealtorListing>> getAllBookmarkedRepos() {
        return this.dao.getAllRepos();
    }

    public LiveData<RealtorListing> getBookmarkedRepoByName(String fullName) {
        return this.dao.getRepoByName(fullName);
    }
}
