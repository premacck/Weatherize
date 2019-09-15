package com.prembros.weatherize.util

import android.content.Context
import android.content.SharedPreferences

import com.prembros.weatherize.WeatherizeApp
import com.prembros.weatherize.util.UnitType.Companion.METRIC

/**
 *
 * Created by Prem$ on 3/31/2018.
 */
object SharedPref {

  private const val OLIVE_BOARD_PREFS = "olive_board_preferences"
  private const val UNIT = "unit"
  private const val FORECAST_LIMIT = "forecast_limit"
  private const val LAST_LOCATION = "last_location"

  private val sharedPreferences: SharedPreferences
    get() = WeatherizeApp.INSTANCE.getSharedPreferences(OLIVE_BOARD_PREFS, Context.MODE_PRIVATE)

  val isMetric: Boolean
    get() = unitType == METRIC

  var lastLocation: String?
    get() = sharedPreferences.getString(LAST_LOCATION, null)
    set(value) = sharedPreferences.edit().putString(LAST_LOCATION, value).apply()

  @UnitType
  var unitType: Int
    get() = sharedPreferences.getInt(UNIT, METRIC)
    set(value) = sharedPreferences.edit().putInt(UNIT, value).apply()

  var forecastLimit: Int
    get() = sharedPreferences.getInt(FORECAST_LIMIT, 3)
    set(value) = sharedPreferences.edit().putInt(FORECAST_LIMIT, value).apply()
}