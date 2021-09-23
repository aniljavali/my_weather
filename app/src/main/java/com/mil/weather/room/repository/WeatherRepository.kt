package com.mil.weather.room.repository

import androidx.lifecycle.LiveData
import com.mil.weather.room.AppDatabase
import com.mil.weather.room.model.Weather

class WeatherRepository(
    private val database: AppDatabase
) {
    suspend fun insertWeather(weather: Weather) = database.getWeatherDao().insertWeather(weather)
    suspend fun deleteWeatherByCityId(cityId: Int) = database.getWeatherDao().deleteWeatherByCityId(cityId)
    fun getCityWeather(cityId: Int?): LiveData<List<Weather>> = database.getWeatherDao().getCityWeather(cityId!!)
}