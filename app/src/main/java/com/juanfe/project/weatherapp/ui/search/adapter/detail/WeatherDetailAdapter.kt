package com.juanfe.project.weatherapp.ui.search.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juanfe.project.weatherapp.R
import com.juanfe.project.weatherapp.databinding.ItemWeatherDetailBinding
import com.juanfe.project.weatherapp.domain.TypeDetail
import com.juanfe.project.weatherapp.domain.WeatherDetailModel


class WeatherDetailAdapter(
    private var list: List<WeatherDetailModel>
) : RecyclerView.Adapter<WeatherDetailAdapter.MyViewHolder>() {

    fun updateList(newList: List<WeatherDetailModel>) {
        val allOrderDiff = WeatherDetailDiffUtil(list, newList)
        val result = DiffUtil.calculateDiff(allOrderDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    /**
     * This method is called when the RecyclerView needs a new view to represent an item.
     * This is where the layout of the RecyclerView items is inflated.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemWeatherDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size


    /**
     * This method is called to assign data to the inflated views.
     * Uses the provided ViewHolder to set up the item view at the specific position.
     */

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemWeatherDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherDetailModel) {
            binding.apply {
                titleWeather.text = item.title
                titleWeather.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    getIcon(item.typeIcon),
                    0,
                    0,
                    0
                )
                getIcon(item.typeIcon)
                if (item.typeIcon == TypeDetail.UV) {
                    infoWeather.text = getScaleUv(item.description)
                } else {
                    infoWeather.text = item.description
                }
            }
        }

        private fun getScaleUv(description: String): String {
            return when (description.toDouble().toInt()) {
                in 1..2 -> "Low"
                in 3..5 -> "Moderate"
                in 6..7 -> "High"
                in 8..10 -> "Very high"
                in 11..Int.MAX_VALUE -> "Very high"
                else -> "unKnown"
            }
        }

        private fun getIcon(typeIcon: TypeDetail): Int {
            return when (typeIcon) {
                TypeDetail.PRESSURE -> R.drawable.compress_24px
                TypeDetail.HUMIDITY -> R.drawable.humidity_percentage_24px
                TypeDetail.WIND -> R.drawable.air_24px
                TypeDetail.VIS -> R.drawable.visibility_24px
                TypeDetail.DEWPOINT -> R.drawable.dew_point_24px
                TypeDetail.UV -> R.drawable.sunny_24px
            }
        }

    }

}