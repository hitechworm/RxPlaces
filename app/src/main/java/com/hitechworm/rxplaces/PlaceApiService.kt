package com.hitechworm.rxplaces

import com.hitechworm.rxplaces.entity.SuggestionResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

enum class PlaceApiServiceStatus(val status: String) {
    OK("OK"),
    ZERO_RESULTS("ZERO_RESULTS"),
    OVER_QUERY_LIMIT("OVER_QUERY_LIMIT"),
    REQUEST_DENIED("REQUEST_DENIED"),
    INVALID_REQUEST("INVALID_REQUEST"),
    UNKNOWN_ERROR("UNKNOWN_ERROR")
}

interface PlaceApiService {
    @GET("/{response_type}")
    fun getAddressSuggestion(@Query("key") key: String,
                             @QueryMap query: Map<String, String?>,
                             @Path("response_type") responseType: String = "json"): Single<SuggestionResult>
}