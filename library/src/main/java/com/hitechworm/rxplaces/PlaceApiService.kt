package com.hitechworm.rxplaces

import com.hitechworm.rxplaces.entity.PlaceDetailResult
import com.hitechworm.rxplaces.entity.SuggestionResult
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

enum class PlaceSuggestApiStatus(val status: String) {
    OK("OK"),
    ZERO_RESULTS("ZERO_RESULTS"),
    OVER_QUERY_LIMIT("OVER_QUERY_LIMIT"),
    REQUEST_DENIED("REQUEST_DENIED"),
    INVALID_REQUEST("INVALID_REQUEST"),
    UNKNOWN_ERROR("UNKNOWN_ERROR")
}

enum class PlaceDetailApiStatus(val status: String) {
    OK("OK"),
    ZERO_RESULTS("ZERO_RESULTS"),
    OVER_QUERY_LIMIT("OVER_QUERY_LIMIT"),
    REQUEST_DENIED("REQUEST_DENIED"),
    INVALID_REQUEST("INVALID_REQUEST"),
    UNKNOWN_ERROR("UNKNOWN_ERROR")
}

interface PlaceApiService {

    @GET("autocomplete/{response_type}")
    fun findPlacePredictions(
            @Path("response_type") responseType: String = "json",
            @Query("input") input: String,
            @QueryMap queries: Map<String, String?>): Call<SuggestionResult>

    @GET("autocomplete/{response_type}")
    fun findRxPlacePredictions(
            @Path("response_type") responseType: String = "json",
            @Query("input") input: String,
            @QueryMap queries: Map<String, String?>): Single<SuggestionResult>

    @GET("details/{response_type}")
    fun getPlaceDetail(@Path("response_type") responseType: String = "json",
                       @Query("key") key: String,
                       @Query("placeid") placeId: String,
                       @QueryMap queries: Map<String, String>): Single<PlaceDetailResult>
}