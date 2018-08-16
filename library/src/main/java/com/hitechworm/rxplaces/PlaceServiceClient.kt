package com.hitechworm.rxplaces

import com.hitechworm.rxplaces.entity.Prediction
import com.hitechworm.rxplaces.entity.SuggestionResult
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceServiceClient internal constructor(apiKey: String) {

    companion object {
        fun create(apiKey: String): PlaceServiceClient {
            return PlaceServiceClient(apiKey)
        }
    }

    private var request: Disposable? = null

    private var apiService = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build().create(PlaceApiService::class.java)

    private val queryMap = mutableMapOf<String, String?>()

    fun type(value: String?) = apply { queryMap["type"] = value }

    fun language(value: String?) = apply { queryMap["language"] = value }

    init {
        queryMap["key"] = apiKey
        type("address")
        language("VN")
    }

    fun getRxAddressPredictions(input: String) =
            apiService.findRxPlacePredictions(input = input, queries = queryMap)
                    .map { it ->
                        if (it.status == PlaceApiServiceStatus.OK.status) {
                            it.predictions
                        } else {
                            null
                        }
                    }

    fun getAddressPredictionsAsync(input: String, successCallback: (List<Prediction>?) -> Unit, errorCallback: ((Throwable) -> Unit)? = null) {
        request?.takeUnless { it.isDisposed }?.dispose()
        request = getRxAddressPredictions(input)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe({ successCallback.invoke(it) }, { errorCallback?.invoke(it) })
    }

    fun getAddressPredictions(input: String) = apiService.findPlacePredictions(input = input, queries = queryMap).execute().result()

    private fun Response<SuggestionResult>.result() =
            when {
                body() != null && PlaceApiServiceStatus.OK.status == body()!!.status -> body()!!.predictions
                else -> null
            }
}