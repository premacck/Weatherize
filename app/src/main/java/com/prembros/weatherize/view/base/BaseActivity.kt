package com.prembros.weatherize.view.base

import androidx.appcompat.app.AppCompatActivity
import com.prembros.weatherize.data.viewmodel.ForecastViewModel
import com.prembros.weatherize.util.initViewModel

/**
 * prem's creation, on 2019-09-06
 */
abstract class BaseActivity : AppCompatActivity() {

  protected val viewModel by lazy { initViewModel<ForecastViewModel>() }
}