package com.hitechworm.rxplaces.entity

import com.google.gson.annotations.SerializedName

class PlaceDetailResult(
        @SerializedName("result") val placeDetail: PlaceDetail?,
        @SerializedName("status") val status: String?) {

    data class PlaceDetail(
            @SerializedName("address_components") val addressComponents: List<AddressComponent?>?,
            @SerializedName("adr_address") val adrAddress: String?,
            @SerializedName("formatted_address") val formattedAddress: String?,
            @SerializedName("geometry") val geometry: Geometry?,
            @SerializedName("icon") val icon: String?,
            @SerializedName("id") val id: String?,
            @SerializedName("name") val name: String?,
            @SerializedName("photos") val photos: List<Photo?>?,
            @SerializedName("place_id") val placeId: String?,
            @SerializedName("reference") val reference: String?,
            @SerializedName("scope") val scope: String?,
            @SerializedName("types") val types: List<String?>?,
            @SerializedName("url") val url: String?,
            @SerializedName("utc_offset") val utcOffset: Int?,
            @SerializedName("vicinity") val vicinity: String?) {

        data class Geometry(
                @SerializedName("location") val location: Location?,
                @SerializedName("viewport") val viewport: Viewport?) {

            data class Location(
                    @SerializedName("lat") val lat: Double?,
                    @SerializedName("lng") val lng: Double?)


            data class Viewport(
                    @SerializedName("northeast") val northeast: Northeast?,
                    @SerializedName("southwest") val southwest: Southwest?) {

                data class Northeast(
                        @SerializedName("lat") val lat: Double?,
                        @SerializedName("lng") val lng: Double?)


                data class Southwest(
                        @SerializedName("lat") val lat: Double?,
                        @SerializedName("lng") val lng: Double?)
            }
        }


        data class Photo(
                @SerializedName("height") val height: Int?,
                @SerializedName("html_attributions") val htmlAttributions: List<String?>?,
                @SerializedName("photo_reference") val photoReference: String?,
                @SerializedName("width") val width: Int?)


        data class AddressComponent(
                @SerializedName("long_name") val longName: String?,
                @SerializedName("short_name") val shortName: String?,
                @SerializedName("types") val types: List<String?>?)
    }
}