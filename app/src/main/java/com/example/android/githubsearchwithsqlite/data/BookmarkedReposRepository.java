package com.example.android.githubsearchwithsqlite.data;

import android.app.Application;

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
}
