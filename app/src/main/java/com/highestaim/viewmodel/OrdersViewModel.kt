package com.highestaim.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.highestaim.deliveryapp.dto.Order
import com.highestaim.deliveryapp.repository.OrdersRepository

class OrdersViewModel(private val ordersRepository: OrdersRepository)  : ViewModel() {

    fun getOrders() : LiveData<List<Order>> {
        return ordersRepository.getOrders()
    }
}