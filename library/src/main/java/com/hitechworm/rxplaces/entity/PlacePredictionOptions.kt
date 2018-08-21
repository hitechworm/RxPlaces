package com.hitechworm.rxplaces.entity

import java.util.*

class PlacePredictionOptions(private var location: String? = null,
                             private var radius: String? = null,
                             private var language: String? = null,
                             private var types: Array<String>? = null,
                             private var countryComponents: Array<String>? = null) {

    fun build() =
            mapOf("location" to location,
                    "radius" to radius,
                    "language" to language,
                    "language" to language,
                    "types" to types?.joinToString(separator = ","),
                    "components" to countryComponents?.joinToString(separator = "|", prefix = "country:"))
                    .filterValues { it != null }
                    .mapValues { it.value!! }

    companion object {
        private fun defaultCountryCode() = Locale.getDefault().country
        fun default() = PlacePredictionOptions(
                types = arrayOf("geocode"),
                language = defaultCountryCode().toUpperCase())
    }
}