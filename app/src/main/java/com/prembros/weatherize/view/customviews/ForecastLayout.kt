package com.prembros.weatherize.view.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.prembros.weatherize.R
import com.prembros.weatherize.data.model.ForecastDay
import com.prembros.weatherize.util.loadUrl
import kotlinx.android.synthetic.main.item_forecast.view.*
import org.jetbrains.anko.dip

/**
 * Prem's creation, on 2019-09-15
 */
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ForecastLayout @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

  private lateinit var forecastDay: ForecastDay

  init {
    View.inflate(context, R.layout.item_forecast, this)
    cardElevation = dip(8).toFloat()
    radius = dip(8).toFloat()
    useCompatPadding = true
  }

  @ModelProp
  fun withForecast(forecastDay: ForecastDay) {
    this.forecastDay = forecastDay
    shimmerize()
    setTexts()
    weather_icon?.loadUrl(forecastDay.day.condition.icon) { deShimmerize() }
  }

  private fun setTexts() {
    if (::forecastDay.isInitialized) {
      forecast_condition?.viewText = forecastDay.day.condition.text
      forecast_temp_max?.viewText = forecastDay.day.suitableMaxTemp
      forecast_temp_min?.viewText = forecastDay.day.suitableMinTemp

      forecast_sun_rise?.viewText = forecastDay.astro.sunrise
      forecast_sun_set?.viewText = forecastDay.astro.sunset
      forecast_moon_rise?.viewText = forecastDay.astro.moonrise
      forecast_moon_set?.viewText = forecastDay.astro.moonset

      forecast_precipitation?.viewText = forecastDay.day.suitablePrecipitation
      forecast_humidity?.viewText = context.getString(R.string.humidity, forecastDay.day.avgHumidity.toString())
    }
  }

  private fun shimmerize() {
    root_layout?.startShimmerAnimation()
  }

  private fun deShimmerize() {
    root_layout?.stopShimmerAnimation()
  }
}