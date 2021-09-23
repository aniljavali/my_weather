package com.mil.weather.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mil.weather.room.dao.CityDao
import com.mil.weather.room.dao.WeatherDao
import com.mil.weather.room.model.City
import com.mil.weather.room.model.Weather

@Database(
    entities = [City::class, Weather::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
    abstract fun getWeatherDao(): WeatherDao

    companion object {
        private const val DB_NAME = "my_weather.db"

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }
}