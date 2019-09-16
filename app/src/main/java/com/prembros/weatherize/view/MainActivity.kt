package com.prembros.weatherize.view

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.prembros.weatherize.R
import com.prembros.weatherize.util.SharedPref
import com.prembros.weatherize.util.hideKeyboard
import com.prembros.weatherize.util.orZero
import com.prembros.weatherize.view.base.BaseActivity
import com.prembros.weatherize.view.base.FragmentNavigation
import com.prembros.weatherize.view.base.LocationPermissionFragment
import com.prembros.weatherize.view.base.LocationPermissionFragment.PermissionAccessListener
import com.prembros.weatherize.view.forecast.MultipleDayForecastFragment
import com.prembros.weatherize.view.forecast.TodayForecastFragment
import com.prembros.weatherize.view.forecast.TomorrowForecastFragment
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener
import com.seatgeek.placesautocomplete.model.Place
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onEditorAction
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : BaseActivity(), FragmentNavigation, OnPlaceSelectedListener,
  PermissionAccessListener {

  private var isRequestingLocationUpdates: Boolean = false
  var currentLocation: Location? = null
  private var locationRequest: LocationRequest? = null
  private lateinit var locationCallback: LocationCallback
  private var fusedLocationProviderClient: FusedLocationProviderClient? = null
  private var sectionsPagerAdapter: SectionsPagerAdapter? = null
  private var dialog: AlertDialog? = null

  companion object {
    private const val REQUESTING_LOCATION_UPDATES_KEY = "locationUpdates"
    private const val KEY_LOCATION = "location"
    private const val REQUEST_LOCATION_PERMISSIONS = 211
    private const val REQUEST_CODE_CHECK_SETTINGS = 312
  }

  override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
    outState?.let {
      it.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, isRequestingLocationUpdates)
      super.onSaveInstanceState(it)
    }
  }

  private fun updateValuesFromBundle(savedInstanceState: Bundle?) {
    // Update the value of mRequestingLocationUpdates from the Bundle.
    if (savedInstanceState != null) {
      if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
        isRequestingLocationUpdates = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY)
      }
      if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
        currentLocation = savedInstanceState.getParcelable(KEY_LOCATION)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    isRequestingLocationUpdates = false
    updateValuesFromBundle(savedInstanceState)
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    initListeners()

    if (arePermissionsAllowed())
      getLastLocation()
    else
      requestPermissionAccess()
  }

  private fun initListeners() {
    search_bar.setOnPlaceSelectedListener(this)
    search_bar.onEditorAction { _, _, _ -> search_bar.hideKeyboard() }
    current_location_weather.onClick {
      search_bar.text = null
      updateCurrentCity()
    }
    current_location_weather.onLongClick { toast(R.string.see_weather_at_your_current_location) }
    container.onPageChangeListener {
      onPageSelected { container.hideKeyboard() }
    }
  }

  override fun onPlaceSelected(place: Place) {
    search_bar.hideKeyboard()
    SharedPref.lastLocation = place.terms[0].value
    prepareTabs(place.terms[0].value)
    search_bar.setText(place.terms[0].value)
  }

  private fun prepareTabs(cityName: String) {
    search_bar.clearFocus()
    if (sectionsPagerAdapter == null) {
      tabs.setupWithViewPager(container)
      sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, cityName)
      container.offscreenPageLimit = 2
      container.adapter = sectionsPagerAdapter
    } else
      sectionsPagerAdapter?.updateCity(cityName)
  }

  fun updateCurrentCity() {
    val geoCoder = Geocoder(this, Locale.getDefault())
    try {
      val addresses = geoCoder.getFromLocation(
        currentLocation?.latitude.orZero(), currentLocation?.longitude.orZero(), 1
      )
      val cityName = addresses[0].locality
      SharedPref.lastLocation = cityName
      prepareTabs(cityName)
    } catch (e: Exception) {
      e.printStackTrace()
      getLastLocation()
    }
  }

  private fun getLastLocation() {
    supportFragmentManager.findFragmentByTag(LocationPermissionFragment::class.java.simpleName)?.let {
      supportFragmentManager.beginTransaction().remove(it).commit()
    }
    if (areLocationServicesEnabled()) {
      if (arePermissionsAllowed()) {
        fusedLocationProviderClient!!.lastLocation
          .addOnSuccessListener(this) { location ->
            if (location != null) {
              currentLocation = location
              updateCurrentCity()
            } else
              createLocationCallback()
          }
          .addOnFailureListener(this) { createLocationCallback() }
      } else
        requestPermissionAccess()
    } else {
      showDialogToEnableLocationServices()
      val lastSavedLocation = SharedPref.lastLocation
      if (lastSavedLocation != null && !lastSavedLocation.contains("null") && lastSavedLocation.isNotEmpty()) {
        prepareTabs(lastSavedLocation)
      }
    }
  }

  private fun areLocationServicesEnabled(): Boolean {
    return try {
      val locationManager = getSystemService(Context.LOCATION_SERVICE) as? LocationManager
      locationManager != null && locationManager.isProviderEnabled(GPS_PROVIDER) && locationManager.isProviderEnabled(NETWORK_PROVIDER)
    } catch (e: Exception) {
      e.printStackTrace()
      false
    }
  }

  private fun showDialogToEnableLocationServices() {
    if (dialog == null) {
      dialog = AlertDialog.Builder(this)
        .setTitle(R.string.turn_on_location)
        .setMessage(R.string.turn_on_location_message)
        .setCancelable(false)
        .setPositiveButton(R.string.enable) { dialog, _ ->
          startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
          dialog.dismiss()
        }
        .setNegativeButton(R.string.close) { _, _ -> search_bar.requestFocus() }
        .create()
    }
    if (dialog?.isShowing == true) dialog?.dismiss()
    dialog?.show()
  }

  private fun createLocationCallback() {
    if (currentLocation == null) {
      locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
          super.onLocationResult(locationResult)
          currentLocation = locationResult!!.lastLocation
          updateCurrentCity()
          stopLocationUpdates()
        }
      }
    } else
      stopLocationUpdates()

    createLocationRequest()
  }

  private fun createLocationRequest() {
    locationRequest = LocationRequest.create()
    locationRequest!!.interval = 10000
    locationRequest!!.fastestInterval = 1000
    locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    val builder = LocationSettingsRequest.Builder()
      .addLocationRequest(locationRequest!!)

    LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())
      .addOnSuccessListener(this) { startLocationUpdates() }
      .addOnFailureListener(this) { e ->
        when ((e as? ApiException)?.statusCode) {
          CommonStatusCodes.RESOLUTION_REQUIRED -> try {
            val resolvable = e as ResolvableApiException
            resolvable.startResolutionForResult(this, REQUEST_CODE_CHECK_SETTINGS)
          } catch (sendEx: Exception) {
            sendEx.printStackTrace()
          }
          LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
            //Location settings are not satisfied.
            //However, we have no way to fix the settings so we won't show the dialog.
          }
        }
      }
    if (isRequestingLocationUpdates) startLocationUpdates()
  }

  private fun startLocationUpdates() {
    if (arePermissionsAllowed() && ::locationCallback.isInitialized) {
      fusedLocationProviderClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    } else requestPermissionAccess()
  }

  private fun stopLocationUpdates() {
    if (::locationCallback.isInitialized) {
      fusedLocationProviderClient?.removeLocationUpdates(locationCallback)?.addOnCompleteListener(this) { isRequestingLocationUpdates = false }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_settings -> {
        startActivity(intentFor<SettingsActivity>())
        true
      }
      else -> false
    }
  }

  override fun pushFragment(fragment: Fragment) {
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.secondary_fragment_container, fragment, fragment::class.java.simpleName)
      .commit()
  }

  override fun popFragment() = supportFragmentManager.popBackStack()

  public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      REQUEST_CODE_CHECK_SETTINGS -> when (resultCode) {
        Activity.RESULT_OK ->
          // All required changes were successfully made
          isRequestingLocationUpdates = true
        Activity.RESULT_CANCELED -> {
          // The user was asked to change settings, but chose not to
          Toast.makeText(this, R.string.location_services_required, Toast.LENGTH_LONG).show()
          isRequestingLocationUpdates = false
        }
      }
    }
  }

  /**
   * PERMISSIONS AND STUFF
   */
  private fun arePermissionsAllowed(): Boolean {
    return checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
        checkSelfPermission(applicationContext, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED &&
        checkSelfPermission(applicationContext, INTERNET) == PERMISSION_GRANTED
  }

  override fun requestPermissionAccess() = ActivityCompat.requestPermissions(
    this,
    arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, INTERNET),
    REQUEST_LOCATION_PERMISSIONS
  )

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
      REQUEST_LOCATION_PERMISSIONS -> if (grantResults.isNotEmpty()) {
        val fineLocationAccepted = grantResults[0] == PERMISSION_GRANTED
        val coarseLocationAccepted = grantResults[1] == PERMISSION_GRANTED
        val internetAccepted = grantResults[2] == PERMISSION_GRANTED

        if (fineLocationAccepted && coarseLocationAccepted && internetAccepted) {
          getLastLocation()
        } else {
          pushFragment(LocationPermissionFragment.newInstance())
        }
      } else pushFragment(LocationPermissionFragment.newInstance())
    }
  }

  inner class SectionsPagerAdapter internal constructor(fm: FragmentManager, private var cityName: String) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
      return when (position) {
        0 -> TodayForecastFragment.newInstance(cityName)
        1 -> TomorrowForecastFragment.newInstance(cityName)
        else -> MultipleDayForecastFragment.newInstance(cityName)
      }
    }

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    fun updateCity(cityName: String) {
      this.cityName = cityName
      notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return when (position) {
        0 -> "Today"
        1 -> "Tomorrow"
        else -> {
          val forecastLimit = SharedPref.forecastLimit
          forecastLimit.toString() + if (forecastLimit > 1) " Days" else " Day"
        }
      }
    }
  }

  override fun onBackPressed() {
    if (tabs.selectedTabPosition != 0) {
      tabs.getTabAt(0)?.select()
    } else
      super.onBackPressed()
  }
}