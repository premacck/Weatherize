package com.prembros.weatherize

import android.app.Application
import com.google.gson.GsonBuilder
import com.prembros.weatherize.network.ApiService
import com.prembros.weatherize.util.APIXU_BASE_URL
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Prem's creation, on 2019-09-06
 */
class WeatherizeApp : Application() {

  private lateinit var apiService: ApiService

  companion object {
    lateinit var INSTANCE: WeatherizeApp
    val api: ApiService
      get() = INSTANCE.apiService
  }

  override fun onCreate() {
    super.onCreate()
    INSTANCE = this

    val retrofit = Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
      .baseUrl(APIXU_BASE_URL)
      .client(
        OkHttpClient.Builder()
          .readTimeout(60, TimeUnit.SECONDS)
          .connectTimeout(60, TimeUnit.SECONDS)
          .addInterceptor { chain ->
            chain.proceed(
              chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .method(chain.request().method(), chain.request().body()).build()
            )
          }
          .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
          .cache(Cache(applicationContext.cacheDir, 20 * 1024 * 1024L))
          .build()
      ).build()
    apiService = retrofit.create(ApiService::class.java)
  }
}