package com.highestaim.deliveryapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.highestaim.deliveryapp.R
import com.highestaim.deliveryapp.dto.Order
import com.highestaim.deliveryapp.util.replaceFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(OrdersFragment(),addBackStack = false)
    }

}