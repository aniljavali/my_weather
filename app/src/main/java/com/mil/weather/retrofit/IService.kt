package com.mil.weather.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IService {
    @GET("forecast?exclude=current,minutely,hourly,alerts&units=imperial&cnt=5")
    fun getForecast(@Query("q") cityState: String, @Query("appid") appId:String): Call<Forecast>
}