package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Astro(
  @SerializedName("sunrise") var sunrise: String = "",
  @SerializedName("sunset") var sunset: String = "",
  @SerializedName("moonrise") var moonrise: String = "",
  @SerializedName("moonset") var moonset: String = ""
) : Parcelable