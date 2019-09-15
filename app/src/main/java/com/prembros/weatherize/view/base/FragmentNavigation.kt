package com.prembros.weatherize.view.base

import androidx.fragment.app.Fragment

/**
 * Created by Prem$ on 3/12/2018.
 */
interface FragmentNavigation {
  fun pushFragment(fragment: Fragment)
  fun popFragment()
}