package com.prembros.weatherize.view

import android.os.Bundle
import com.prembros.weatherize.R
import com.prembros.weatherize.util.SharedPref
import com.prembros.weatherize.util.UnitType
import com.prembros.weatherize.util.UnitType.Companion.IMPERIAL
import com.prembros.weatherize.util.UnitType.Companion.METRIC
import com.prembros.weatherize.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener

class SettingsActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    toggleTempUnit(SharedPref.unitType, false)
    initListeners()
    forecast_limit_selector.setSelection(SharedPref.forecastLimit - 1)
  }

  private fun initListeners() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    forecast_limit_selector.onItemSelectedListener {
      onItemSelected { _, _, position, _ -> SharedPref.forecastLimit = position + 1 }
    }
    ctv_metric.onClick { toggleTempUnit(if (!ctv_metric.isChecked) METRIC else IMPERIAL, true) }

    ctv_imperial.onClick { toggleTempUnit(if (!ctv_imperial.isChecked) IMPERIAL else METRIC, true) }
  }

  private fun toggleTempUnit(@UnitType unitType: Int, isAction: Boolean) {
    if (isAction) {
      ctv_metric.isChecked = !ctv_metric.isChecked
      ctv_imperial.isChecked = !ctv_imperial.isChecked
    }
    when (unitType) {
      METRIC -> {
        ctv_metric.setBackgroundResource(R.drawable.bg_btn_accent_curved_left)
        ctv_imperial.setBackgroundResource(R.drawable.bg_btn_white_curved_right)
      }
      IMPERIAL -> {
        ctv_metric.setBackgroundResource(R.drawable.bg_btn_white_curved_left)
        ctv_imperial.setBackgroundResource(R.drawable.bg_btn_accent_curved_right)
      }
    }
    SharedPref.unitType = unitType
  }
}