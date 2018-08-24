package com.hitechworm.rxplaces.entity

import com.google.gson.annotations.SerializedName

data class Prediction(
        @SerializedName("description") val description: String,
        @SerializedName("id") val id: String,
        @SerializedName("place_id") val placeId: String,
        @SerializedName("structured_formatting") val structuredFormatting: StructuredFormatting?,
        @SerializedName("terms") val terms: List<Term>,
        @SerializedName("types") val types: List<String?>) {
    override fun toString() = description
}

data class StructuredFormatting(
        @SerializedName("main_text") val mainText: String?,
        @SerializedName("main_text_matched_substrings") val mainTextMatchedSubstrings: List<MatchedSubstring?>?,
        @SerializedName("secondary_text") val secondaryText: String?) {

    data class MatchedSubstring(
            @SerializedName("length") val length: Int?,
            @SerializedName("offset") val offset: Int?
    )
}

data class Term(
        @SerializedName("offset") val offset: Int?,
        @SerializedName("value") val value: String?)

