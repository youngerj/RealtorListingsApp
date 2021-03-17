package com.example.android.realtorlistingsapp.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RealtorService {
//    @Headers({
//            "x-rapidapi-key: 1294738560mshc23cbbdaf97d21ep129eb8jsnac327fc7d636",
//            "x-rapidapi-host: realtor.p.rapidapi.com"
//    })
    @GET("/properties/v2/list-for-sale")
    Call<RealtorSearchResults> searchRepos(
            //@Query("q") String query);
            @Query("city") String city,
            @Query("limit") String limit,
            @Query("offset") String offset,
            @Query("state_code") String state,
            @Query("beds_min") String beds,
            @Query("price_min") String price_min,
            @Query("sort") String sort,
            @Query("baths") String baths,
            @Query("price_max") String price_max,
            @Header("x-rapidapi-key") String key,
            @Header("x-rapidapi-host") String host
    );

}
