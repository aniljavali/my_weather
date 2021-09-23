package com.mil.weather.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mil.weather.room.model.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City)

    @Query("SELECT * FROM City ORDER BY cityName DESC")
    fun getAllCities(): LiveData<List<City>>

    @Query("SELECT * FROM City WHERE cityId = :id")
    fun getCityById(id: Int): LiveData<City>
}