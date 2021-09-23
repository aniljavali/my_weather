package com.mil.weather.room.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mil.weather.room.repository.WeatherRepository

class WeatherFactory(
    private val repository: WeatherRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(WeatherRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            Log.e("WeatherFactory", e.message.toString())
        }
        return super.create(modelClass)
    }
}