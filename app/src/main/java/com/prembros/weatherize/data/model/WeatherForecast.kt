package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.prembros.weatherize.util.DayType
import com.prembros.weatherize.util.getDateHeader
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherForecast(
  @SerializedName("location") var location: Location = Location(),
  @SerializedName("current") var current: Current = Current(),
  @SerializedName("forecast") var forecast: Forecast = Forecast(),
  @DayType private var dayType: Int = 0
) : Parcelable {

  val today: String?
    get() = try {
      current.lastUpdateEpoch.getDateHeader()
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }

  val tomorrow: String?
    get() = try {
      forecast.forecastDay.getOrNull(1)?.dateEpoch?.getDateHeader()
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }

  val humidity: Long
    get() = try {
      when (dayType) {
        DayType.TODAY -> current.humidity
        DayType.TOMORROW -> forecast.forecastDay.getOrNull(1)?.day?.avgHumidity?.toLong() ?: 0
        else -> 0
      }
    } catch (e: Exception) {
      e.printStackTrace()
      0
    }

  val condition: String?
    get() = try {
      when (dayType) {
        DayType.TODAY -> current.condition.text
        DayType.TOMORROW -> forecast.forecastDay.getOrNull(1)?.day?.condition?.text
        else -> null
      }
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }
}