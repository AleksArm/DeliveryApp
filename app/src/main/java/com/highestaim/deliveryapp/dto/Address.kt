package com.highestaim.deliveryapp.dto

import com.google.android.gms.maps.model.LatLng

data class Address (
    var city: String? = null,
    var address: String? = null,
    val coordinates: LatLng? = null
)