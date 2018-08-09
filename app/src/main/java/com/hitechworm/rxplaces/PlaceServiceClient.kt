package com.hitechworm.rxplaces

import com.hitechworm.rxplaces.entity.Prediction
import com.hitechworm.rxplaces.entity.SuggestionResult
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceServiceClient internal constructor(private val apiKey: String) {

    companion object {
        fun create(apiKey: String): PlaceServiceClient {
            return PlaceServiceClient(apiKey)
        }
    }

    private var apiService = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build().create(PlaceApiService::class.java)

    private val queryMap = mutableMapOf<String, String?>()

    fun type(value: String?) = apply { queryMap["types"] = value }

    fun language(value: String?) = apply { queryMap["language"] = value }

    init {
        type("vn")
        language("vn")
    }

    fun getAddressPredictions(input: String, response: (status: PlaceApiServiceStatus, predictions: List<Prediction>) -> Unit):
            Single<SuggestionResult> {
        queryMap["input"] = input
        return apiService.getAddressSuggestion(apiKey, queryMap.filterValues { it != null })
                .doOnSuccess {
                    response.invoke(PlaceApiServiceStatus.valueOf(it.status), it.predictions)
                }
    }
}