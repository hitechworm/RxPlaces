package com.hitechworm.rxplaces.entity

import com.google.gson.annotations.SerializedName

class PlaceDetailResult(
        @SerializedName("result") val placeDetail: PlaceDetail?,
        @SerializedName("status") val status: String?)