package com.hitechworm.rxplaces

import com.hitechworm.rxplaces.entity.PlaceDetailOptions
import com.hitechworm.rxplaces.entity.PlacePredictionOptions
import com.hitechworm.rxplaces.entity.Prediction
import com.hitechworm.rxplaces.entity.SuggestionResult
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceServiceClient internal constructor(val apiKey: String) {

    companion object {
        fun create(apiKey: String): PlaceServiceClient {
            return PlaceServiceClient(apiKey)
        }
    }

    private var request: Disposable? = null

    private var apiService = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build().create(PlaceApiService::class.java)

    fun requestGetPlaceDetail(placeId: String, options: PlaceDetailOptions = PlaceDetailOptions.default()) =
            apiService.getPlaceDetail(key = apiKey, placeId = placeId, queries = options.build())
                    .map { it ->
                        if (it.status == PlaceSuggestApiStatus.OK.status) {
                            it.placeDetail
                        } else {
                            null
                        }
                    }

    fun requestGetAddressPredictions(input: String, options: PlacePredictionOptions = PlacePredictionOptions.default()) =
            apiService.findRxPlacePredictions(input = input, queries = options.build())
                    .map { it ->
                        if (it.status == PlaceSuggestApiStatus.OK.status) {
                            it.predictions
                        } else {
                            null
                        }
                    }

    fun getAddressPredictionsAsync(input: String, successCallback: (List<Prediction>?) -> Unit, errorCallback: ((Throwable) -> Unit)? = null) {
        request?.takeUnless { it.isDisposed }?.dispose()
        request = requestGetAddressPredictions(input)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe({ successCallback.invoke(it) }, { errorCallback?.invoke(it) })
    }

    fun getAddressPredictions(input: String, options: PlacePredictionOptions = PlacePredictionOptions.default()) =
            apiService.findPlacePredictions(input = input, queries = options.build()).execute().result()

    private fun Response<SuggestionResult>.result() =
            when {
                body() != null && PlaceSuggestApiStatus.OK.status == body()!!.status -> body()!!.predictions
                else -> null
            }
}