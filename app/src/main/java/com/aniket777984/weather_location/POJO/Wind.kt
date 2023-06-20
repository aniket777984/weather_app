package com.aniket777984.weather_location.POJO

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") val speed:Double,
    @SerializedName("deg") val deg:Int
)
