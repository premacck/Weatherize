package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.prembros.weatherize.util.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Current(
  @SerializedName("last_updated_epoch") var lastUpdateEpoch: Long = 0,
  @SerializedName("wind_degree") var windDegree: Long = 0,
  @SerializedName("humidity") var humidity: Long = 0,
  @SerializedName("cloud") var cloud: Long = 0,
  @SerializedName("last_updated") var lastUpdated: String = "",
  @SerializedName("wind_dir") var windDir: String = "",
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
  @SerializedName("condition") var condition: Condition = Condition()
) : Parcelable {
  val suitableTemp: String
    get() = if (SharedPref.isMetric) tempC.toString() + CELSIUS else tempF.toString() + FAHRENHEIT

  val suitableWind: String
    get() = if (SharedPref.isMetric) windKph.toString() + KPH else windMph.toString() + MPH
}