package com.prembros.weatherize.view.forecast

import android.graphics.Color
import com.airbnb.epoxy.Typed2EpoxyController
import com.prembros.weatherize.data.model.ForecastDay
import com.prembros.weatherize.util.getDateHeader
import com.prembros.weatherize.view.customviews.ForecastLayoutModel_
import com.prembros.weatherize.view.customviews.HeaderTextViewModel_

/**
 * Prem's creation, on 2019-09-15
 */
class ForecastController : Typed2EpoxyController<String, List<ForecastDay>>() {

  override fun buildModels(cityName: String?, forecastDays: List<ForecastDay>?) {
    if (!forecastDays.isNullOrEmpty()) {
      HeaderTextViewModel_()
        .id(0)
        .withHeader("Weather in $cityName")
        .withPaddingTop(14)
        .withPaddingBottom(14)
        .withTextSize(24)
        .withTextColor(Color.parseColor("#333333"))
        .addTo(this)

      forecastDays.forEach { forecast ->
        HeaderTextViewModel_()
          .id("Header:$forecast")
          .withHeader(forecast.dateEpoch.getDateHeader())
          .withPaddingTop(24)
          .withPaddingBottom(4)
          .withTextSize(14)
          .withTextColor(Color.parseColor("#80000000"))
          .addTo(this)

        ForecastLayoutModel_()
          .id(forecast.toString())
          .withForecast(forecast)
          .addTo(this)
      }
    }
  }
}