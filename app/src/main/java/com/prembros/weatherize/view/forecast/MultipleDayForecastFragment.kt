package com.prembros.weatherize.view.forecast

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import com.prembros.weatherize.R
import com.prembros.weatherize.util.CITY_NAME
import com.prembros.weatherize.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_multiple_day_forecast.*
import org.jetbrains.anko.support.v4.toast

class MultipleDayForecastFragment : BaseFragment() {

  private lateinit var controller: ForecastController
  private lateinit var cityName: String

  companion object {
    fun newInstance(cityName: String) = MultipleDayForecastFragment().apply {
      arguments = Bundle().apply { putString(CITY_NAME, cityName) }
    }
  }

  override val layoutResource: Int
    get() = R.layout.fragment_multiple_day_forecast

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    cityName = arguments?.getString(CITY_NAME).orEmpty()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initRecyclerView()
    initListeners()
    progress_bar?.visibility = VISIBLE
    getMultipleDayForeCast()
  }

  private fun initRecyclerView() {
    controller = ForecastController()
    forecast_list.setController(controller)
    forecast_list?.layoutAnimation = LayoutAnimationController(
      AnimationUtils.loadAnimation(context, android.R.anim.fade_in), 0.2f
    )
  }

  private fun initListeners() {
    swipe_refresh_layout?.setOnRefreshListener { getMultipleDayForeCast() }
  }

  private fun getMultipleDayForeCast() {
    if (::cityName.isInitialized)
      viewModel.getMultipleDayForecast(cityName).observe(this, Observer {
        controller.setData(it.location.name, it.forecast.forecastDay)
        dismissLoaders()
      })
    else toast(R.string.could_not_find_city)
  }

  private fun dismissLoaders() {
    progress_bar?.visibility = GONE
    if (swipe_refresh_layout?.isRefreshing == true) swipe_refresh_layout?.isRefreshing = false
  }
}