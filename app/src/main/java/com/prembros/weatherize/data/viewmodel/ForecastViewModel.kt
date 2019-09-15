package com.prembros.weatherize.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.prembros.weatherize.network.ApiRepository
import com.prembros.weatherize.network.ApiRepositoryImpl

/**
 * Prem's creation, on 2019-09-06
 */
class ForecastViewModel : ViewModel() {

  private val repository: ApiRepository = ApiRepositoryImpl()
  lateinit var location: String

  fun getTodayForecast(location: String) = liveData(viewModelScope.coroutineContext) {
    emit(repository.getTodayForecast(location))
  }

  fun getTomorrowForecast(location: String) = liveData(viewModelScope.coroutineContext) {
    emit(repository.getTomorrowForecast(location))
  }

  fun getMultipleDayForecast(location: String) = liveData(viewModelScope.coroutineContext) {
    emit(repository.getMultipleDayForecast(location))
  }
}