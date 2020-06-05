package com.highestaim.deliveryapp.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.highestaim.deliveryapp.R

fun Fragment.replaceFragment(fragment: Fragment, addBackStack: Boolean) {
    if (addBackStack) {
        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
            ?.replace(R.id.fragmentContainer, fragment)
            ?.commitAllowingStateLoss()
    } else {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, fragment)
            ?.commitAllowingStateLoss()
    }
}


fun AppCompatActivity.replaceFragment(fragment: Fragment, addBackStack: Boolean) {
    if (addBackStack) {
        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commitAllowingStateLoss()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commitAllowingStateLoss()
    }
}

fun Fragment.replaceTabFragment(fragment: Fragment, addBackStack: Boolean = false) {

    if (addBackStack) {
        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
            ?.replace(R.id.frame_container, fragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            ?.commitAllowingStateLoss()
    } else {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_container, fragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            ?.commitAllowingStateLoss()
    }
}


fun <R> RecyclerView.initRecyclerView(context: Context?, adapter: R, isVertical: Boolean = true) {
    context?.let {
        this.layoutManager = LinearLayoutManager(
            context,
            if (isVertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL,
            false
        )
        this.adapter = adapter as RecyclerView.Adapter<*>
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showIf(condition: Boolean) = if (condition) show() else hide()


@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("MissingPermission")
fun Context.requestSingleLocation(locationCallBack: (LatLng) -> Unit): Boolean {
    if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return false
    }
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    if (isGPSEnabled) {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        locationManager.requestSingleUpdate(criteria, object : LocationListener {
            override fun onLocationChanged(p0: Location?) {
                p0?.let {
                    locationCallBack.invoke(LatLng(it.latitude, it.longitude))
                }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {

            }
        }, null)
    } else {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE
        locationManager.requestSingleUpdate(criteria, object : LocationListener {
            override fun onLocationChanged(p0: Location?) {
                p0?.let {
                    locationCallBack.invoke(LatLng(it.latitude, it.longitude))
                }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {

            }
        }, null)
    }
    return true
}




