package com.example.android.realtorlistingsapp;

import com.example.android.realtorlistingsapp.data.RealtorListing;
import com.example.android.realtorlistingsapp.data.RealtorSearchRepository;
import com.example.android.realtorlistingsapp.data.LoadingStatus;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RealtorSearchViewModel extends ViewModel {
    private LiveData<List<RealtorListing>> searchResults;
    private LiveData<LoadingStatus> loadingStatus;
    private RealtorSearchRepository repository;

    public RealtorSearchViewModel() {
        this.repository = new RealtorSearchRepository();
        this.searchResults = this.repository.getSearchResults();
        this.loadingStatus = this.repository.getLoadingStatus();
    }

//    public void loadSearchResults(String query) {
//        this.repository.loadSearchResults(query);
//    }

    public void loadSearchResults(String query, String city, String state, String sort,
                                  String beds, String baths, String price_min, String price_max) {
        this.repository.loadSearchResults(query, city, state,
                sort, beds, baths, price_min, price_max);
    }

    public LiveData<List<RealtorListing>> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }
}
