package com.mil.weather.room.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mil.weather.room.model.Weather
import com.mil.weather.room.repository.WeatherRepository

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    suspend fun insertWeather(weather: Weather) = repository.insertWeather(weather)
    suspend fun deleteWeatherByCityId(cityId: Int) = repository.deleteWeatherByCityId(cityId)
    fun getCityWeather(cityId: Int?): LiveData<List<Weather>> = repository.getCityWeather(cityId!!)
}