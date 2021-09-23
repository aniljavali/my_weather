package com.mil.weather.room.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mil.weather.room.model.City
import com.mil.weather.room.repository.CityRepository

class CityViewModel(
    private val repository: CityRepository
) : ViewModel() {

    suspend fun insertCity(note: City) = repository.insertCity(note)
    fun getCityById(id: Int): LiveData<City> = repository.getCityById(id)
    fun getAllCities(): LiveData<List<City>> = repository.getAllCities()
}