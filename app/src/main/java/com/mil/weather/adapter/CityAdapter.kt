package com.mil.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mil.weather.databinding.RowCityBinding
import com.mil.weather.room.model.City

class CityAdapter(
    list: List<City>,
    private val onItemClicked: (City) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private val _list: List<City> = list
    private lateinit var binding: RowCityBinding

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var city: TextView = binding.tvCity
        var state: TextView = binding.tvState
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = RowCityBinding.inflate(layoutInflater)
        return CityViewHolder(binding.root)
    }

    override fun onBindViewHolder(cityViewHolder: CityViewHolder, position: Int) {
        cityViewHolder.city.text = _list[position].cityName
        cityViewHolder.state.text = _list[position].state
        binding.root.setOnClickListener {
            onItemClicked(_list[position])
        }
    }

    override fun getItemCount(): Int {
        return _list.size
    }
}