package com.prembros.weatherize.view.customviews

import android.content.Context
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  var viewText: CharSequence?
    get() = text
    set(value) {
      try {
        if (value != null && !value.contains("null") && value.isNotEmpty()) {
          setBackgroundColor(TRANSPARENT)
          super.setText(value)
          visibility = VISIBLE
        } else if (value != null && value.isEmpty()) {
          setBackgroundColor(Color.parseColor("#E0E0E0"))
        } else
          visibility = GONE
      } catch (e: Exception) {
        e.printStackTrace()
        visibility = GONE
      }
    }

  fun shimmerize() {
    viewText = ""
  }

  fun animateViewIn(delay: Long) {
    postDelayed({ visibility = View.VISIBLE }, delay)
  }
}