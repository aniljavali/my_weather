package com.mil.weather.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @PrimaryKey(autoGenerate = true)
    var cityId: Int? = null,
    val cityName: String,
    val state: String
)