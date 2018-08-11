@file:Suppress("unused")

package com.hitechworm.rxplaces.adapter

class SimplePlaceSuggestAdapter(googleApiKey: String) : BasePlaceSuggestAdapter(googleApiKey) {
    override fun getItemLayout() = android.R.layout.simple_dropdown_item_1line
}