package com.prembros.weatherize.network

import com.prembros.weatherize.data.model.WeatherForecast
import com.prembros.weatherize.util.APIXU_API_KEY
import com.prembros.weatherize.util.SharedPref
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Prem's creation, on 2019-09-06
 */
interface ApiService {

  @GET("/v1/forecast.json?key=$APIXU_API_KEY")
  suspend fun getTodayForecast(@Query("q") location: String, @Query("days") days: Int = 1): WeatherForecast

  @GET("/v1/forecast.json?key=$APIXU_API_KEY")
  suspend fun getTomorrowForecast(@Query("q") location: String, @Query("days") days: Int = 2): WeatherForecast

  @GET("/v1/forecast.json?key=$APIXU_API_KEY")
  suspend fun getMultipleDayForecast(@Query("q") location: String, @Query("days") days: Int = SharedPref.forecastLimit): WeatherForecast
}