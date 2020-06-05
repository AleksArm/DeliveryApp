package com.highestaim.deliveryapp.service

import com.google.android.gms.maps.model.LatLng
import com.highestaim.deliveryapp.dto.Address
import com.highestaim.deliveryapp.dto.Order

object MockDataService {

    fun getOrders(): List<Order> {
        return arrayListOf(
            Order(
                id = 456546,
                address = Address(
                    city = "Yerevan",
                    address = "Isahakyan 10",
                    coordinates = LatLng(40.1849966,44.5221241)
                ),
                estimatedTime = "08:12",
                startTime = "08:00",
                endTime = "08:30"
            ),
            Order(
                id = 45468797,
                address = Address(
                    city = "Yerevan",
                    address = " Isahakyan 15",
                    coordinates = LatLng(40.1872814,44.5195362)
                ),
                estimatedTime = "08:40",
                startTime = "08:00",
                endTime = "08:30"
            ),
            Order(
                id = 451231234,
                address = Address(
                    city = "Yerevan",
                    address = "Moskovyan 5",
                    coordinates = LatLng(40.1840969,44.5193617)
                ),
                estimatedTime = "09:20",
                startTime = "09:00",
                endTime = "09:30"
            ),
            Order(
                id = 45678676,
                address = Address(
                    city = "Yerevan",
                    address = "55 Mesrop Mashtots ",
                    coordinates = LatLng(40.1902095,44.5167242)
                ),
                estimatedTime = "10:20",
                startTime = "10:00",
                endTime = "08:30"
            ),
            Order(
                id = 487679645,
                address = Address(
                    city = "Yerevan",
                    address = "Koryun 15",
                    coordinates = LatLng(40.1884434,44.5202929)
                ),
                estimatedTime = "14:20",
                startTime = "14:00",
                endTime = "15:30"
            ),
            Order(
                id = 45789976,
                address = Address(
                    city = "Yerevan",
                    address = "Teryan 6",
                    coordinates = LatLng(40.1799052,44.5091135)
                ),
                estimatedTime = "20:20",
                startTime = "19:00",
                endTime = "20:30"
            ),
            Order(
                id = 49797635,
                address = Address(
                    city = "Yerevan",
                    address = "Komitas 15",
                    coordinates = LatLng(40.2035902,44.4971255)
                ),
                estimatedTime = "13:20",
                startTime = "12:00",
                endTime = "13:30"
            )
        )
    }
}