package com.juanfe.project.weatherapp.ui.search.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juanfe.project.weatherapp.databinding.ItemLocationBinding
import com.juanfe.project.weatherapp.domain.SearchModel


class SearchHistoryAdapter(
    private var list: List<SearchModel>,
    private val onItemSelected: (SearchModel, Boolean) -> Unit
) : RecyclerView.Adapter<SearchHistoryAdapter.MyViewHolder>() {

    fun updateList(newList: List<SearchModel>) {
        val allOrderDiff = SearchHistoryDiffUtil(list, newList)
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
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size


    /**
     * This method is called to assign data to the inflated views.
     * Uses the provided ViewHolder to set up the item view at the specific position.
     */

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, onItemSelected)
    }

    class MyViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchModel, onItemSelected: (SearchModel, Boolean) -> Unit) {
            binding.apply {

                val location = "${item.name}, ${item.region}, ${item.country}"
                historyLocation.text = location

                historyArrow.setOnClickListener {
                    onItemSelected.invoke(item, false)
                }

                historySchedule.setOnClickListener {
                    onItemSelected.invoke(item, true)
                }

                historyLocation.setOnClickListener {
                    onItemSelected.invoke(item, true)
                }

            }
        }

    }

}