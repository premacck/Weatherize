package com.prembros.weatherize.view.forecast

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.prembros.weatherize.R
import com.prembros.weatherize.data.model.WeatherForecast
import com.prembros.weatherize.util.CITY_NAME
import com.prembros.weatherize.util.loadUrl
import com.prembros.weatherize.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_one_day_forecast.*
import org.jetbrains.anko.support.v4.toast

class TomorrowForecastFragment : BaseFragment() {

  private lateinit var cityName: String

  companion object {
    fun newInstance(cityName: String) = TomorrowForecastFragment().apply {
      arguments = Bundle().apply { putString(CITY_NAME, cityName) }
    }
  }

  override val layoutResource: Int
    get() = R.layout.fragment_one_day_forecast

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    cityName = arguments?.getString(CITY_NAME).orEmpty()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    swipe_refresh_layout.setOnRefreshListener { getTomorrowForecast() }
    getTomorrowForecast()
  }

  private fun getTomorrowForecast() {
    if (::cityName.isInitialized) {
      shimmerize()
      viewModel.getTomorrowForecast(cityName).observe(this, Observer { handleResponse(it) })
    } else toast(R.string.could_not_find_city)
  }

  private fun handleResponse(weatherForecast: WeatherForecast) {
    val tomorrowForecast = weatherForecast.forecast.forecastDay.getOrNull(1)
    weather_icon?.loadUrl(tomorrowForecast?.day?.condition?.icon)
    weather_city_name?.viewText = cityName
    weather_date?.viewText = weatherForecast.tomorrow
    weather_temperature?.viewText = tomorrowForecast?.day?.suitableAvgTemp
    weather_wind_speed?.viewText = tomorrowForecast?.day?.suitableMaxWind
    weather_wind_degree?.viewText = null
    weather_humidity?.viewText = getString(R.string.humidity, weatherForecast.humidity.toString())
    weather_condition?.viewText = getString(R.string.condition, weatherForecast.condition)
    dismissLoaders()
  }

  private fun dismissLoaders() {
    deShimmerize()
    if (swipe_refresh_layout?.isRefreshing == true) swipe_refresh_layout?.isRefreshing = false
  }

  private fun shimmerize() {
    root_layout?.startShimmerAnimation()
    weather_city_name?.shimmerize()
    weather_date?.shimmerize()
    weather_temperature?.shimmerize()
    weather_wind_speed?.shimmerize()
    weather_wind_degree?.shimmerize()
    weather_humidity?.shimmerize()
    weather_condition?.shimmerize()
  }

  private fun deShimmerize() {
    root_layout?.stopShimmerAnimation()
  }
}