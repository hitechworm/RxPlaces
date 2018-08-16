package com.hitechworm.rxplaces

import com.hitechworm.rxplaces.entity.SuggestionResult
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class PlaceServiceClient internal constructor(private val apiKey: String) {

    companion object {
        fun create(apiKey: String): PlaceServiceClient {
            return PlaceServiceClient(apiKey)
        }
    }

    private var apiService = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build().create(PlaceApiService::class.java)

    private var request: Call<SuggestionResult>? = null

    private val queryMap = mutableMapOf<String, String?>()

    fun type(value: String?) = apply { queryMap["type"] = value }

    fun language(value: String?) = apply { queryMap["language"] = value }

    init {
        queryMap["key"] = apiKey
        type("address")
        language("VN")
    }

    fun cancel() = request?.takeUnless { it.isCanceled }?.cancel()

    fun getAddressPredictions(input: String,
                              responseCallback: (SuggestionResult) -> Unit,
                              errorCallback: ((Throwable) -> Unit)?) {
        cancel()
        request = apiService.findPlacePredictions(input, queryMap).apply {
            val response = execute()
            if (response.isSuccessful) {
                responseCallback.invoke(response.body()!!)
            } else {
                errorCallback?.invoke(Exception(response.errorBody()?.string()))
            }
        }
    }
}