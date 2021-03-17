package com.example.android.realtorlistingsapp.utils;

import android.text.TextUtils;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class RealtorUtils {
    private final static String REALTOR_SEARCH_BASE_URL = "https://realtor.p.rapidapi.com/properties/v2";
    private final static String REALTOR_SEARCH_CITY_FORMAT_STR = "city=%s";
    private final static String REALTOR_SEARCH_STATE_FORMAT_STR = "state_code=%s";
    private final static String REALTOR_SEARCH_LIMIT = "limit=200";
    private final static String REALTOR_SEARCH_OFFSET = "offset=0";
    private final static String REALTOR_SEARCH_SORT_FORMAT_STR = "sort=%s";
    private final static String REALTOR_SEARCH_BEDS_FORMAT_STR = "beds_min=%s";
    private final static String REALTOR_SEARCH_BATHS_FORMAT_STR = "baths_min=%s";
    private final static String REALTOR_SEARCH_PRICE_MIN_FORMAT_STR = "price_min=%s";
    private final static String REALTOR_SEARCH_PRICE_MAX_FORMAT_STR = "price_max=%s";

    public static String buildRealtorSearchQueryTerm(String query, String city, String state,
                                                    String sort, String beds,
                                                    String baths, String price_min,
                                                    String price_max) {
        String queryTerm = new String(query);

        queryTerm += " " + String.format(REALTOR_SEARCH_CITY_FORMAT_STR, city);
        queryTerm += " " + REALTOR_SEARCH_LIMIT;
        queryTerm += " " + REALTOR_SEARCH_OFFSET;
        queryTerm += " " + String.format(REALTOR_SEARCH_STATE_FORMAT_STR, state);

        if (!TextUtils.isEmpty(beds)) {
            queryTerm += " " + String.format(REALTOR_SEARCH_BEDS_FORMAT_STR, beds);
        }

        if (!TextUtils.isEmpty(price_min)) {
            queryTerm += " " + String.format(REALTOR_SEARCH_PRICE_MIN_FORMAT_STR, price_min);
        }

        if (!TextUtils.isEmpty(sort)) {
            queryTerm += " " + String.format(REALTOR_SEARCH_SORT_FORMAT_STR, sort);
        }

        if (!TextUtils.isEmpty(baths)) {
            queryTerm += " " + String.format(REALTOR_SEARCH_BATHS_FORMAT_STR, baths);
        }

        if (!TextUtils.isEmpty(price_max)) {
            queryTerm += " " + String.format(REALTOR_SEARCH_PRICE_MAX_FORMAT_STR, price_max);
        }

        return queryTerm;
    }

}
