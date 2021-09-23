package com.mil.weather.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mil.weather.room.model.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @Transaction
    @Query("SELECT * FROM Weather WHERE cityId = :cityId ORDER BY weatherId")
    fun getCityWeather(cityId: Int): LiveData<List<Weather>>

    @Query("DELETE FROM Weather WHERE cityId = :cityId")
    suspend fun deleteWeatherByCityId(cityId: Int)
}