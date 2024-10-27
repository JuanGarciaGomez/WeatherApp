package com.juanfe.project.weatherapp.ui.search.adapter.detail

import androidx.recyclerview.widget.DiffUtil
import com.juanfe.project.weatherapp.domain.WeatherDetailModel


class WeatherDetailDiffUtil(
    private val oldList: List<WeatherDetailModel>,
    private val newList: List<WeatherDetailModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // I can use whatever validations that i need
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}