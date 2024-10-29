package com.juanfe.project.weatherapp.ui.search.adapter.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juanfe.project.weatherapp.core.ex.getDayOfWeek
import com.juanfe.project.weatherapp.core.ex.loadProductImg
import com.juanfe.project.weatherapp.databinding.ItemForecastDayBinding
import com.juanfe.project.weatherapp.domain.ForecastDayModel


class ForecastDayAdapter(
    private var list: List<ForecastDayModel>
) : RecyclerView.Adapter<ForecastDayAdapter.MyViewHolder>() {

    fun updateList(newList: List<ForecastDayModel>) {
        val allOrderDiff = ForecastDayDiffUtil(list, newList)
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
            ItemForecastDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class MyViewHolder(private val binding: ItemForecastDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForecastDayModel) {
            binding.apply {
                val temp = "${item.day.avgTempC.toInt()}°C"
                val rain = "${item.day.dailyChanceOfRain.toInt()}%"
                forecastDate.text = item.date.getDayOfWeek()
                weatherImg.loadProductImg(item.day.condition.icon)
                avgTemp.text = temp
                chanceRain.text = rain
            }
        }

    }

}