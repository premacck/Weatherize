package com.prembros.weatherize.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.prembros.weatherize.R

/**
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

fun ImageView.loadUrl(url: String?, action: (resource: Drawable?) -> Unit = {}) {
  if (!url.isNullOrEmpty()) {
    Glide.with(this)
      .load(HTTPS + url)
      .placeholder(R.drawable.image_view_shimmer)
      .listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean = false
        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
          action(resource)
          return false
        }
      }).into(this)
  }
}