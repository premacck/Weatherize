package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(@SerializedName("forecastday") var forecastDay: ArrayList<ForecastDay> = ArrayList()) :
  Parcelable