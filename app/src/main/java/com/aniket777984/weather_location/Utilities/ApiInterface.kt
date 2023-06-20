package com.aniket777984.weather_location.Utilities

import com.aniket777984.weather_location.POJO.ModelClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getCurrentWeatherData(
        @Query("lat") latitude:Double,
        @Query("lon") longitude:Double,
        @Query("appid") api_key:String
    ):Call<ModelClass>

    @GET("weather")
    fun getCityWeatherData(
        @Query("q") cityName:String,
        @Query("appid") api_key:String
    ):Call<ModelClass>

}