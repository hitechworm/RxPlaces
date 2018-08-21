package com.hitechworm.rxplaces.entity

enum class FieldType(val value: String) {
    ADDRESS_COMPONENTS("address_components"),
    FORMATTED_ADDRESS("formatted_address"),
    FORMATTED_PHONE_NUMBER("formatted_phone_number"),
    INTERNATIONAL_PHONE_NUMBER("international_phone_number"),
    ADR_ADDRESS("adr_address"),
    GEOMETRY("geometry"),
    ICON("icon"),
    NAME("name"),
    OPENING_HOURS("opening_hours"),
    PERMANENTLY_CLOSED("permanently_closed"),
    PHOTOS("photos"),
    PRICE_LEVEL("price_level"),
    RATING("rating"),
    REVIEWS("reviews"),
    WEBSITE("website")
}

class PlaceDetailOptions(private var fields: Array<FieldType>? = null,
                         private var language: String? = null,
                         private var region: String? = null) {

    companion object {
        fun default() = PlaceDetailOptions()
    }

    fun build() = mapOf(
            "fields" to fields?.joinToString(transform = { it.value }, separator = ","),
            "language" to language,
            "region" to region)
            .filterValues { it != null }
            .mapValues { it.value!! }
}