package com.example.android.githubsearchwithsqlite;

import android.app.Application;

import com.example.android.githubsearchwithsqlite.data.BookmarkedReposRepository;
import com.example.android.githubsearchwithsqlite.data.GitHubRepo;

import androidx.lifecycle.AndroidViewModel;

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
}
