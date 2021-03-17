package com.example.android.realtorlistingsapp.data;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.realtorlistingsapp.utils.RealtorUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.format.TextStyle;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RealtorSearchRepository {
    private static final String TAG = RealtorSearchRepository.class.getSimpleName();
    private static final String BASE_URL = "https://realtor.p.rapidapi.com/properties/v2/list-for-sale/";

    private MutableLiveData<List<RealtorListing>> searchResults;
    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentQuery;
    private String currentSort;
    private String currentCity;
    private String currentState;
    private String currentBeds;
    private String currentBaths;
    private String currentMinPrice;
    private String currentMaxPrice;

    private RealtorService realtorService;

    public RealtorSearchRepository() {
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ListingAddress.class, new ListingAddress.JsonDeserializer())
                .registerTypeAdapter(ListingAgent.class, new ListingAgent.JsonDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.realtorService = retrofit.create(RealtorService.class);
    }

    public LiveData<List<RealtorListing>> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

//    private boolean shouldExecuteSearch(String query) {
//        return !TextUtils.equals(query, this.currentQuery)
//                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
//    }

    private boolean shouldExecuteSearch(String query, String sort, String city, String state,
                                        String beds, String baths, String minPrice, String maxPrice) {
        return !TextUtils.equals(query, this.currentQuery)
                || !TextUtils.equals(sort, this.currentSort)
                || !TextUtils.equals(city, this.currentCity)
                || !TextUtils.equals(state, this.currentState)
                || !TextUtils.equals(beds, this.currentBeds)
                || !TextUtils.equals(baths, this.currentBaths)
                || !TextUtils.equals(minPrice, this.currentMinPrice)
                || !TextUtils.equals(maxPrice, this.currentMaxPrice)
                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
    }

//    public void loadSearchResults(String query) {
//        if (this.shouldExecuteSearch(query)) {
//            Log.d(TAG, "running new search for this query: " + query);
//            this.currentQuery = query;
//            this.executeSearch(query);
//        } else {
//            Log.d(TAG, "using cached search results for this query: " + query);
//        }
//    }

    public void loadSearchResults(String query, String city, String state, String sort,
                                  String beds, String baths, String price_min, String price_max) {
        if (this.shouldExecuteSearch(query, sort, city, state, beds, baths, price_min, price_max)) {

            this.currentQuery = query;
            this.currentSort = sort;
            this.currentCity = city;
            this.currentState = state;
            this.currentBeds = beds;
            this.currentBaths = baths;
            this.currentMinPrice = price_min;
            this.currentMaxPrice = price_max;
            String queryTerm = RealtorUtils.buildRealtorSearchQueryTerm(query, city, state,
                    sort, beds, baths, price_min, price_max);
            Log.d(TAG, "running new search for this query: " + queryTerm);
            //this.executeSearch(queryTerm);
            this.executeSearch(city,"200","0", state, beds, sort, price_min, baths, price_max);
        } else {
            Log.d(TAG, "using cached search results for this query: " + query);
        }
    }

    private void executeSearch(String city, String limit, String offset, String state, String beds, String sort, String price_min, String baths, String price_max) {
        Call<RealtorSearchResults> results;

        results = this.realtorService.searchRepos(city, limit, offset, state, beds, price_min, sort, baths, price_max, "1294738560mshc23cbbdaf97d21ep129eb8jsnac327fc7d636", "realtor.p.rapidapi.com");

        this.searchResults.setValue(null);
        this.loadingStatus.setValue(LoadingStatus.LOADING);

        results.enqueue(new Callback<RealtorSearchResults>() {
            @Override
            public void onResponse(Call<RealtorSearchResults> call, Response<RealtorSearchResults> response) {
                if (response.code() == 200) {
                    //Log.d(TAG, String.valueOf(response.body().properties.get(0).address.get(0).line));
                    //Log.d(TAG, String.valueOf(response.body().properties.get(0).agents.get(0).name));
                    searchResults.setValue(response.body().properties);
                    loadingStatus.setValue(LoadingStatus.SUCCESS);
                } else {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, call.request().url().toString());
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<RealtorSearchResults> call, Throwable t) {
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
            }
        });
    }
}
