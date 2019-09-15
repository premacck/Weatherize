package com.prembros.weatherize.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.prembros.weatherize.data.viewmodel.ForecastViewModel
import com.prembros.weatherize.util.initViewModel

/**
 * Created by Prem$ on 3/30/2018.
 */
abstract class BaseFragment : Fragment() {

  protected var navigation: FragmentNavigation? = null
  protected val viewModel by lazy { initViewModel<ForecastViewModel>() }

  val parentActivity: BaseActivity
    get() = activity as BaseActivity

  @get:LayoutRes
  abstract val layoutResource: Int

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(layoutResource, container, false)

  override fun onAttach(context: Context) {
    super.onAttach(context)
    navigation = context as FragmentNavigation
  }

  override fun onDestroy() {
    super.onDestroy()
    navigation = null
  }
}