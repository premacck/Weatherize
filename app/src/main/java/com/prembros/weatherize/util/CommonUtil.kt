package com.prembros.weatherize.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import java.text.SimpleDateFormat
import java.util.*

/**
 * Prem's creation, on 2019-09-06
 */
inline fun <reified T : ViewModel> Fragment.initViewModel(): T = ViewModelProviders.of(this).get(T::class.java)

inline fun <reified T : ViewModel> AppCompatActivity.initViewModel(): T = ViewModelProviders.of(this).get(T::class.java)

fun Long.getDateHeader(): String {
  return SimpleDateFormat("EEEE dd, hh:mm a", Locale.getDefault())
    .format(Date(this * 1000L))
}

fun Long?.orZero(): Long = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0