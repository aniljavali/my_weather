package com.mil.weather.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    var weatherId: Int? = null,
    val cityId: Int,
    val date: Long,
    val temp: Double,
    val icon: String?
)