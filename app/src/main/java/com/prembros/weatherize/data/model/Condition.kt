package com.prembros.weatherize.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Condition(
  @SerializedName("text") var text: String = "",
  @SerializedName("icon") var icon: String = "",
  @SerializedName("code") var code: Int = 0
) : Parcelable
