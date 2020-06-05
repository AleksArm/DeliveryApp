package com.highestaim.deliveryapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.highestaim.deliveryapp.dto.Order
import com.highestaim.deliveryapp.service.MockDataService

class OrdersRepository {

    fun getOrders(): LiveData<List<Order>> {
        val orders = MutableLiveData<List<Order>>()


        orders.value = MockDataService.getOrders()


        return orders
    }

}