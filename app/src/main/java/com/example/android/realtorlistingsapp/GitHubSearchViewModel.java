package com.example.android.realtorlistingsapp;

import com.example.android.realtorlistingsapp.data.GitHubRepo;
import com.example.android.realtorlistingsapp.data.GitHubSearchRepository;
import com.example.android.realtorlistingsapp.data.LoadingStatus;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class GitHubSearchViewModel extends ViewModel {
    private LiveData<List<GitHubRepo>> searchResults;
    private LiveData<LoadingStatus> loadingStatus;
    private GitHubSearchRepository repository;

    public GitHubSearchViewModel() {
        this.repository = new GitHubSearchRepository();
        this.searchResults = this.repository.getSearchResults();
        this.loadingStatus = this.repository.getLoadingStatus();
    }

    public void loadSearchResults(String query) {
        this.repository.loadSearchResults(query);
    }

    public void loadSearchResults(String query, String sort, String language, String user,
                                  boolean inName, boolean inDescription, boolean inReadme) {
        this.repository.loadSearchResults(query, sort, language, user, inName, inDescription, inReadme);
    }

    public LiveData<List<GitHubRepo>> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }
}
