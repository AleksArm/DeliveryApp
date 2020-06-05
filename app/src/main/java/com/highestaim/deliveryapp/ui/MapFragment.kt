package com.highestaim.deliveryapp.ui

import android.Manifest
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.AvoidType
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.highestaim.deliveryapp.R
import com.highestaim.deliveryapp.dto.Order
import com.highestaim.deliveryapp.enum.MapEnum
import com.highestaim.deliveryapp.util.AppConstants.MAP_CAMERA_ZOOM
import com.highestaim.deliveryapp.util.AppConstants.MAP_ROUTE_WIDTH
import com.highestaim.deliveryapp.util.AppKee.OPEN_ENUM
import com.highestaim.deliveryapp.util.AppKee.ORDER
import com.highestaim.deliveryapp.service.MockDataService.getOrders
import com.highestaim.deliveryapp.service.PreferenceService
import com.highestaim.deliveryapp.util.Utils.getMarkerIconFromDrawable
import com.highestaim.deliveryapp.util.requestSingleLocation
import com.innfinity.permissionflow.lib.permissionFlow
import com.innfinity.permissionflow.lib.withFragment
import com.innfinity.permissionflow.lib.withPermissions
import kotlinx.android.synthetic.main.map_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MapFragment : BaseFragment() {

    private var googleMap: GoogleMap? = null
    private var mapOpenEnum: MapEnum? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView?.onCreate(savedInstanceState)
        initMap()
        initMapEnum()
    }

    override fun getLayoutId() = R.layout.map_fragment

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }


    private fun initMap() {
        mapView?.getMapAsync {
            googleMap = it

            if (mapOpenEnum == MapEnum.FROM_TAB) {
                PreferenceService.get().getOrders()?.get(0)?.address?.coordinates?.let { location -> cameraMoveTo(location) }
                addingOrdersMarkers()
            } else if (mapOpenEnum == MapEnum.FROM_NAVIGATE) {
                getCurrentLocation()
            }

        }
    }

    private fun addingOrdersMarkers() {
        val orders = PreferenceService.get().getOrders()
        orders?.let {
            for (order in it) {
                googleMap?.addMarker(
                    order.address?.coordinates?.let {latLang ->
                        MarkerOptions()
                            .position(latLang)
                            .title(order.address?.address)
                    }
                )
            }
        }

    }

    private fun cameraMoveTo(latLng: LatLng) {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_CAMERA_ZOOM))
    }

    private fun getCurrentLocation() {
            val isPermissionGranted = activity?.requestSingleLocation {
                cameraMoveTo(LatLng(it.latitude, it.longitude))

                setCurrentLocationMarker(it)
                setDestinationMarker(getSelectedOrder())

                drawRoute(currentLocation = it, destinationLocation = getSelectedOrderLocation())
            }
            isPermissionGranted?.let {
                if (!it) {
                    locationPermission()
                }
            }
    }


    private fun locationPermission() {
        CoroutineScope(Dispatchers.Main).launch {
            permissionFlow {
                withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

                withFragment(this@MapFragment)

                requestEach().collect {
                    getCurrentLocation()
                }
            }
        }
    }

    private fun getSelectedOrderLocation(): LatLng? {
        return (arguments?.getSerializable(ORDER) as Order).address?.coordinates
    }

    private fun getSelectedOrder(): Order? {
        return arguments?.getSerializable(ORDER) as Order
    }

    private fun drawRoute(currentLocation: LatLng, destinationLocation: LatLng?) {
        GoogleDirection.withServerKey(getString(R.string.google_maps_key))
            .from(currentLocation)
            .to(destinationLocation)
            .avoid(AvoidType.FERRIES)
            .avoid(AvoidType.HIGHWAYS)
            .transitMode(TransportMode.DRIVING)
            .execute(object : DirectionCallback {
                override fun onDirectionSuccess(direction: Direction) {
                    if (direction.isOK) {
                        val route: Route = direction.routeList[0]
                        val leg = route.legList[0]
                        val opts =
                            PolylineOptions().addAll(leg.directionPoint).color(Color.RED).width(MAP_ROUTE_WIDTH)
                        googleMap?.addPolyline(opts)
                    } else {
                        Log.e("Direction Error", direction.errorMessage)
                    }
                }

                override fun onDirectionFailure(t: Throwable) {
                }
            })
    }

    private fun setCurrentLocationMarker(currentLocation: LatLng) {
        val drawable = context?.let { ContextCompat.getDrawable(it, R.drawable.navigation) }
        val markerIcon = drawable?.let { getMarkerIconFromDrawable(it) }

        googleMap?.addMarker(
            MarkerOptions()
                .position(currentLocation)
                .icon(markerIcon)
        )
    }

    private fun setDestinationMarker(order: Order?) {
        order?.address?.coordinates?.let {
            googleMap?.addMarker(
                MarkerOptions()
                    .position(it)
                    .title(order.address?.address)
            )
        }
    }


    private fun initMapEnum() {
        mapOpenEnum = arguments?.getSerializable(OPEN_ENUM) as MapEnum
    }
}