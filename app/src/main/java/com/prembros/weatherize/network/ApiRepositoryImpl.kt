package com.prembros.weatherize.network

import com.prembros.weatherize.WeatherizeApp
import com.prembros.weatherize.data.model.WeatherForecast
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class ApiRepositoryImpl : ApiRepository {

  private val service: ApiService = WeatherizeApp.api

  override suspend fun getTodayForecast(location: String): WeatherForecast =
    withContext(Default) { service.getTodayForecast(location) }

  override suspend fun getTomorrowForecast(location: String): WeatherForecast =
    withContext(Default) { service.getTomorrowForecast(location) }

  override suspend fun getMultipleDayForecast(location: String): WeatherForecast =
    withContext(Default) { service.getMultipleDayForecast(location) }
}