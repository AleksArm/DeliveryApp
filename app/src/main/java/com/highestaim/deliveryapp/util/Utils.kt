package com.highestaim.deliveryapp.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor? {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    @SuppressLint("SimpleDateFormat")
    fun isTimeOut(deliveryStartTime: String?, deliveryEndTime: String?, currentTime: String?): Boolean {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.US)
        val openDate = parseDate(dateFormat, deliveryStartTime)
        val closeDate = parseDate(dateFormat, deliveryEndTime)
        val currentDate = parseDate(dateFormat, currentTime)

        return if (openDate.before(closeDate)) {
            if (currentDate.before(openDate)) {
                true
            } else currentDate.after(closeDate)
        } else {
            currentDate.after(closeDate) && currentDate.before(openDate)
        }
    }

    private fun parseDate(dateFormat: SimpleDateFormat, date: String?): Date {
        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            Date(0)
        }
    }
}