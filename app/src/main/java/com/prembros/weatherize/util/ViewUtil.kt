package com.prembros.weatherize.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.prembros.weatherize.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 *
 * Created by Prem$ on 3/31/2018.
 */
fun View.hideKeyboard() {
  try {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
  } catch (e: Exception) {
    e.printStackTrace()
  }
}

fun ImageView.loadUrl(url: String?, action: () -> Unit = {}) {
  if (!url.isNullOrEmpty()) {
    Picasso.get().load(HTTP + url)
      .placeholder(R.drawable.image_view_shimmer)
      .into(this, object : Callback {
        override fun onError(e: java.lang.Exception?) = action()
        override fun onSuccess() = action()
      })
  }
}