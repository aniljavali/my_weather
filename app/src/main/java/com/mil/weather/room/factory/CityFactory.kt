package com.mil.weather.room.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mil.weather.room.repository.CityRepository

class CityFactory(
    private val repository: CityRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(CityRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            Log.e("CityFactory", e.message.toString())
        }
        return super.create(modelClass)
    }
}