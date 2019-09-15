package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hour(
  @SerializedName("time_epoch") var timeEpoch: Long = 0,
  @SerializedName("wind_degree") var windDegree: Long = 0,
  @SerializedName("humidity") var humidity: Long = 0,
  @SerializedName("cloud") var cloud: Long = 0,
  @SerializedName("will_it_rain") var willItRain: Long = 0,
  @SerializedName("will_it_snow") var willItSnow: Long = 0,
  @SerializedName("time") var time: String? = null,
  @SerializedName("wind_dir") var windDir: String? = null,
  @SerializedName("temp_c") var tempC: Double = 0.0,
  @SerializedName("temp_f") var tempF: Double = 0.0,
  @SerializedName("wind_mph") var windMph: Double = 0.0,
  @SerializedName("wind_kph") var windKph: Double = 0.0,
  @SerializedName("pressure_mb") var pressureMb: Double = 0.0,
  @SerializedName("pressure_in") var pressureIn: Double = 0.0,
  @SerializedName("precip_mm") var precipMm: Double = 0.0,
  @SerializedName("precip_in") var precipIn: Double = 0.0,
  @SerializedName("feelslike_c") var feelslikeC: Double = 0.0,
  @SerializedName("feelslike_f") var feelslikeF: Double = 0.0,
  @SerializedName("windchill_c") var windchillC: Double = 0.0,
  @SerializedName("windchill_f") var windchillF: Double = 0.0,
  @SerializedName("heatindex_c") var heatindexC: Double = 0.0,
  @SerializedName("heatindex_f") var heatindexF: Double = 0.0,
  @SerializedName("dewpoint_c") var dewpointC: Double = 0.0,
  @SerializedName("dewpoint_f") var dewpointF: Double = 0.0,
  @SerializedName("condition") var condition: Condition? = null
) : Parcelable