package com.prembros.weatherize.util

import androidx.annotation.IntDef
import com.prembros.weatherize.util.DayType.Companion.MULTIPLE_DAYS
import com.prembros.weatherize.util.DayType.Companion.TODAY
import com.prembros.weatherize.util.DayType.Companion.TOMORROW
import com.prembros.weatherize.util.UnitType.Companion.IMPERIAL
import com.prembros.weatherize.util.UnitType.Companion.METRIC

/**
 * Created by Prem$ on 3/30/2018.
 */
@Retention
@IntDef(TODAY, TOMORROW, MULTIPLE_DAYS)
annotation class DayType {
  companion object {
    const val TODAY = 0
    const val TOMORROW = 1
    const val MULTIPLE_DAYS = 2
  }
}

@Retention
@IntDef(METRIC, IMPERIAL)
annotation class UnitType {
  companion object {
    const val METRIC = 0
    const val IMPERIAL = 1
  }
}
