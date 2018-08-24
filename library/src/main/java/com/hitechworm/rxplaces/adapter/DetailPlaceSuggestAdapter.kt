@file:Suppress("unused")

package com.hitechworm.rxplaces.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hitechworm.rxplaces.entity.Prediction

class DetailPlaceSuggestAdapter(googleApiKey: String) : BasePlaceSuggestAdapter(googleApiKey) {
    override fun getItemLayout() = android.R.layout.simple_list_item_2


    private fun getInflater(context: Context): LayoutInflater {
        inflater = inflater ?: LayoutInflater.from(context)
        return inflater!!
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?) =
            bindView(view ?: getInflater(parent!!.context).inflate(getItemLayout(), parent, false), getItem(position))


    override fun bindView(view: View, prediction: Prediction): View {
        (view.tag as ItemHolder? ?: ItemHolder(view)).bind(prediction)
        return view
    }

    private class ItemHolder(view: View) {

        val text1 = view.findViewById<TextView>(android.R.id.text1)

        val text2 = view.findViewById<TextView>(android.R.id.text2)

        fun bind(prediction: Prediction) {
            text1.text = prediction.structuredFormatting?.mainText ?: ""
            text2.text = prediction.structuredFormatting?.secondaryText ?: ""
        }
    }
}