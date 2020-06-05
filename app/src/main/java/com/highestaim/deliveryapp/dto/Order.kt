package com.highestaim.deliveryapp.dto

import java.io.Serializable

data class Order (
    var id : Int? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var estimatedTime: String? = null,
    var address: Address? = null,
    var isSelected:Boolean = false,
    var isFinished: Boolean = false,
    var isTimeOut: Boolean = false
) : Serializable