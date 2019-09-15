package com.prembros.weatherize.network

import com.prembros.weatherize.data.model.WeatherForecast

interface ApiRepository {

  suspend fun getTodayForecast(location: String): WeatherForecast

  suspend fun getTomorrowForecast(location: String): WeatherForecast

  suspend fun getMultipleDayForecast(location: String): WeatherForecast
}