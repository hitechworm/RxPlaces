package com.hitechworm.rxplaces.entity

import com.google.gson.annotations.SerializedName

data class Prediction(
        @SerializedName("description") val description: String,
        @SerializedName("id") val id: String,
        @SerializedName("place_id") val placeId: String,
        @SerializedName("matched_substrings") val matchedSubstrings: List<MatchedSubstring?>,
        @SerializedName("terms") val terms: List<Term>,
        @SerializedName("types") val types: List<String?>) {
    override fun toString() = description
}

data class MatchedSubstring(
        @SerializedName("length") val length: Int?,
        @SerializedName("offset") val offset: Int?)

data class Term(
        @SerializedName("offset") val offset: Int?,
        @SerializedName("value") val value: String?)

