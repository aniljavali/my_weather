package com.mil.weather.room.repository

import androidx.lifecycle.LiveData
import com.mil.weather.room.AppDatabase
import com.mil.weather.room.model.City

class CityRepository(
    private val database: AppDatabase
) {
    suspend fun insertCity(city: City) = database.getCityDao().insertCity(city)
    fun getCityById(id: Int): LiveData<City> = database.getCityDao().getCityById(id)
    fun getAllCities(): LiveData<List<City>> = database.getCityDao().getAllCities()
}