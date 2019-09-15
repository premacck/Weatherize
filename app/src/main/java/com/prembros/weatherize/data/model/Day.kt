package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.prembros.weatherize.util.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Day(
  @SerializedName("maxtemp_c") var maxTempC: Double = 0.0,
  @SerializedName("maxtemp_f") var maxTempF: Double = 0.0,
  @SerializedName("mintemp_c") var minTempC: Double = 0.0,
  @SerializedName("mintemp_f") var minTempF: Double = 0.0,
  @SerializedName("avgtemp_c") var avgTempC: Double = 0.0,
  @SerializedName("avgtemp_f") var avgTempF: Double = 0.0,
  @SerializedName("maxwind_mph") var maxWindMph: Double = 0.0,
  @SerializedName("maxwind_kph") var maxWindKph: Double = 0.0,
  @SerializedName("totalprecip_mm") var totalPrecipMm: Double = 0.0,
  @SerializedName("totalprecip_in") var totalPrecipIn: Double = 0.0,
  @SerializedName("avghumidity") var avgHumidity: Double = 0.0,
  @SerializedName("condition") var condition: Condition = Condition()
) : Parcelable {
  val suitableAvgTemp: String
    get() = if (SharedPref.isMetric) avgTempC.toString() + DEGREE + CELSIUS else avgTempF.toString() + DEGREE + FAHRENHEIT

  val suitableMaxTemp: String
    get() = if (SharedPref.isMetric) maxTempC.toString() + DEGREE + CELSIUS else maxTempF.toString() + DEGREE + FAHRENHEIT

  val suitableMinTemp: String
    get() = if (SharedPref.isMetric) minTempC.toString() + DEGREE + CELSIUS else minTempF.toString() + DEGREE + FAHRENHEIT

  val suitablePrecipitation: String
    get() = if (SharedPref.isMetric) totalPrecipMm.toString() + MM else totalPrecipIn.toString() + IN

  val suitableMaxWind: String
    get() = if (SharedPref.isMetric) maxWindKph.toString() + KPH else maxWindMph.toString() + MPH
}