package com.prembros.weatherize.view.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER
import androidx.appcompat.widget.AppCompatCheckedTextView
import org.jetbrains.anko.dip

class CustomCheckedTextView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatCheckedTextView(context, attrs, defStyleAttr) {

  init {
    setAttributes()
  }

  private fun setAttributes() {
    val defaultPadding = dip(14)
    setPadding(defaultPadding, defaultPadding / 2, defaultPadding, defaultPadding / 2)
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    setTextColor(Color.parseColor("#333333"))
    gravity = CENTER
  }

  override fun setBackgroundResource(resId: Int) {
    super.setBackgroundResource(resId)
    setAttributes()
  }
}