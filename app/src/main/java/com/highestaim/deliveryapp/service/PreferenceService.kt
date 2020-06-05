package com.highestaim.deliveryapp.service

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.highestaim.deliveryapp.dto.Order

class PreferenceService {

    private var ordersSharedPreference: SharedPreferences? = null

    private var context: Context? = null

    private val gson by lazy { Gson() }


    fun saveOrders(orders: List<Order>) {
        ordersSharedPreference?.edit()?.putString("order", Gson().toJson(orders))?.apply()
    }


    fun getOrders(): List<Order>? {
        val stringData = ordersSharedPreference?.getString("order", null)
        if (stringData != null) {
            val type = object : TypeToken<List<Order>>() {
            }.type
            return gson.fromJson(stringData, type) as List<Order>
        }
        return null
    }

    private fun initSharedPreferences() {
        if (ordersSharedPreference == null) {
            ordersSharedPreference = context?.getSharedPreferences("savedOrders",
                Context.MODE_PRIVATE
            )
        }
    }


    fun injectContext(context: Context): PreferenceService {
        if (this.context == null) {
            this.context = context
            initSharedPreferences()
        }
        return get()
    }


    companion object {
        private var preferenceService: PreferenceService? = null

        fun get(): PreferenceService {
            if (preferenceService == null) {
                preferenceService = PreferenceService()
            }
            return preferenceService as PreferenceService
        }
    }
}