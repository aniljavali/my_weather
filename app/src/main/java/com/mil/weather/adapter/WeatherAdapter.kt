package com.mil.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mil.weather.R
import com.mil.weather.databinding.RowWeatherBinding
import com.mil.weather.room.model.Weather
import kotlin.math.roundToInt

class ForecastAdapter(
    context: Context?,
    list: List<Weather>
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    private val _list: List<Weather> = list
    private var _context: Context? = context
    private lateinit var binding: RowWeatherBinding

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var temp: TextView = binding.tvTemp
        var icon: ImageView = binding.ivIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = RowWeatherBinding.inflate(layoutInflater)
        return ForecastViewHolder(binding.root)
    }

    override fun onBindViewHolder(forecastViewHolder: ForecastViewHolder, position: Int) {
        forecastViewHolder.temp.text = _context!!.resources.getString(R.string.degree_f, _list[position].temp.roundToInt())
        Glide.with(_context!!)
            .load(_context!!.resources.getString(R.string.icon_url, _list[position].icon))
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(forecastViewHolder.icon);
    }

    override fun getItemCount(): Int {
        return _list.size
    }
}