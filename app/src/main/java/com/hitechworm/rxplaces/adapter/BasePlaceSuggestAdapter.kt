package com.hitechworm.rxplaces.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.hitechworm.rxplaces.PlaceApiServiceStatus
import com.hitechworm.rxplaces.PlaceServiceClient
import com.hitechworm.rxplaces.entity.Prediction

abstract class BasePlaceSuggestAdapter(googleApiKey: String) : BaseAdapter(), Filterable {

    private var data: MutableList<Prediction>? = null
    private var inflater: LayoutInflater? = null
    private var filter: Filter? = null
    var placeServiceClient = PlaceServiceClient.create(googleApiKey)

    override fun getItem(position: Int) = data!![position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = when (data) {
        null -> 0
        else -> data!!.size
    }

    abstract fun getItemLayout(): Int

    override fun getView(position: Int, view: View?, parent: ViewGroup?) =
            when (view) {
                null -> {
                    inflater = inflater ?: LayoutInflater.from(parent!!.context)
                    val itemView = inflater!!.inflate(getItemLayout(), parent, false)
                    bindView(itemView, getItem(position))
                }
                else -> bindView(view, getItem(position))
            }

    open fun bindView(view: View, prediction: Prediction): View {
        if (view is TextView) {
            view.text = prediction.description
        }
        return view
    }

    override fun getFilter(): Filter {
        filter = filter ?: PlaceFilter()
        return filter!!
    }

    inner class PlaceFilter : Filter() {
        override fun performFiltering(text: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            text?.let {
                placeServiceClient.getAddressPredictions(text.toString())
                { status, predictions -> filterResults.handleResponse(status, predictions) }
            }
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(text: CharSequence?, result: FilterResults?) {
            result?.let {
                data?.clear()
                data?.addAll(result.values as List<Prediction>)
            }
        }

        private fun FilterResults.handleResponse(status: PlaceApiServiceStatus, predictions: List<Prediction>) {
            if (status == PlaceApiServiceStatus.OK) {
                values = predictions
                count = predictions.size
            }
        }
    }
}