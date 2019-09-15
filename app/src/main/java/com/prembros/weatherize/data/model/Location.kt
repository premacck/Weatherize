package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
  @SerializedName("name") var name: String? = null,
  @SerializedName("region") var region: String? = null,
  @SerializedName("country") var country: String? = null,
  @SerializedName("tz_id") var tzId: String? = null,
  @SerializedName("localtime") var localtime: String? = null,
  @SerializedName("lat") var lat: Double = 0.toDouble(),
  @SerializedName("lon") var long: Double = 0.toDouble(),
  @SerializedName("localtime_epoch") var localtimeEpoch: Long = 0
) : Parcelable
