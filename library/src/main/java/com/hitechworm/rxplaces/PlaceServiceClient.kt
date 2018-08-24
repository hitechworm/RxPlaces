package com.hitechworm.rxplaces

import com.hitechworm.rxplaces.entity.PlaceDetail
import com.hitechworm.rxplaces.entity.PlaceDetailOptions
import com.hitechworm.rxplaces.entity.PlaceDetailResult
import com.hitechworm.rxplaces.entity.PlacePredictionOptions
import com.hitechworm.rxplaces.entity.Prediction
import com.hitechworm.rxplaces.entity.SuggestionResult
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PlaceServiceClient internal constructor(private val apiKey: String) {

    companion object {
        fun create(apiKey: String): PlaceServiceClient {
            return PlaceServiceClient(apiKey)
        }
    }

    private var request: Disposable? = null

    private var apiService = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient()).build()
            .create(PlaceApiService::class.java)

    fun requestGetPlaceDetail(placeId: String, options: PlaceDetailOptions = PlaceDetailOptions.default()): Single<PlaceDetail?> =
            apiService.getPlaceDetail(key = apiKey, placeId = placeId, queries = options.build())
                    .map { it.takeIf { it.isOk() }?.placeDetail }

    fun requestGetAddressPredictions(input: String, options: PlacePredictionOptions = PlacePredictionOptions.default()): Single<List<Prediction>?> =
            apiService.findRxPlacePredictions(key = apiKey, input = input, queries = options.build())
                    .map { it.takeIf { it.isOk() }?.predictions }

    fun getAddressPredictionsAsync(input: String, successCallback: (List<Prediction>?) -> Unit, errorCallback: ((Throwable) -> Unit)? = null) {
        request?.takeUnless { it.isDisposed }?.dispose()
        request = requestGetAddressPredictions(input)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe({ successCallback.invoke(it) }, { errorCallback?.invoke(it) })
    }

    fun getAddressPredictions(input: String, options: PlacePredictionOptions = PlacePredictionOptions.default()): List<Prediction>? {
        return apiService.findPlacePredictions(key = apiKey, input = input, queries = options.build()).execute().predictions()
    }

    private fun PlaceDetailResult.isOk() = PlaceDetailApiStatus.OK.status == status

    private fun SuggestionResult.isOk() = PlaceSuggestApiStatus.OK.status == status

    private fun Response<SuggestionResult>.predictions() = body()?.takeIf { it.isOk() }?.predictions
}