package com.prembros.weatherize.view.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.prembros.weatherize.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.themedTextView

/**
 * Prem's creation, on 2019-09-15
 */
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HeaderTextView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private var textView: TextView = themedTextView(theme = R.style.AppTheme_CustomTextView_Default) {
    setPaddingRelative(dip(16), dip(24), dip(16), dip(4))
  }

  @TextProp
  fun withHeader(text: CharSequence?) {
    textView.text = text
  }

  @ModelProp(ModelProp.Option.DoNotHash)
  fun withTextSize(size: Int) = textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())

  @ModelProp(ModelProp.Option.DoNotHash)
  fun withTextColor(colorInt: Int) = textView.setTextColor(colorInt)

  @ModelProp(ModelProp.Option.DoNotHash)
  fun withPaddingStart(padding: Int) = setPaddingRelative(dip(padding), paddingTop, paddingEnd, paddingBottom)

  @ModelProp(ModelProp.Option.DoNotHash)
  fun withPaddingTop(padding: Int) = setPaddingRelative(paddingStart, dip(padding), paddingEnd, paddingBottom)

  @ModelProp(ModelProp.Option.DoNotHash)
  fun withPaddingEnd(padding: Int) = setPaddingRelative(paddingStart, paddingTop, dip(padding), paddingBottom)

  @ModelProp(ModelProp.Option.DoNotHash)
  fun withPaddingBottom(padding: Int) = setPaddingRelative(paddingStart, paddingTop, paddingEnd, dip(padding))
}