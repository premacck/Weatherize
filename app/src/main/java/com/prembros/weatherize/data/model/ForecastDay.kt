package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ForecastDay(
  @SerializedName("date") var date: String = "",
  @SerializedName("date_epoch") var dateEpoch: Long = 0,
  @SerializedName("day") var day: Day = Day(),
  @SerializedName("astro") var astro: Astro = Astro(),
  @SerializedName("hour") var hour: ArrayList<Hour> = ArrayList()
) : Parcelable