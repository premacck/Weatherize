package com.prembros.weatherize.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prembros.weatherize.R
import kotlinx.android.synthetic.main.fragment_location_permission.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LocationPermissionFragment : Fragment() {

  private var listener: PermissionAccessListener? = null

  companion object {
    fun newInstance() = LocationPermissionFragment()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    listener = context as PermissionAccessListener
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_location_permission, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    turn_on_location_btn.onClick { listener?.requestPermissionAccess() }
  }

  interface PermissionAccessListener {
    fun requestPermissionAccess()
  }
}