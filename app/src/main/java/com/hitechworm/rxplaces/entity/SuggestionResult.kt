package com.hitechworm.rxplaces.entity

import com.google.gson.annotations.SerializedName


data class SuggestionResult(@SerializedName("status") val status: String,
                            @SerializedName("predictions") val predictions: List<Prediction>
)