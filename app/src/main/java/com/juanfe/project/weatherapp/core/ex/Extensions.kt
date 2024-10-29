package com.juanfe.project.weatherapp.core.ex

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.juanfe.project.weatherapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun ImageView.loadProductImg(img: String) {
    val url = img.replace("//", "https://")
    pretty(url).into(this)
}

fun View.pretty(url: String): RequestBuilder<Drawable> {
    return Glide
        .with(this)
        .load(url)
        .fitCenter()
        .placeholder(R.mipmap.ic_launcher_round)
}


fun String.getDayOfWeek(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = format.parse(this) ?: return "Invalid Date"
    val calendar = Calendar.getInstance().apply { time = date }

    val today = Calendar.getInstance()
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    today.set(Calendar.SECOND, 0)
    today.set(Calendar.MILLISECOND, 0)

    val inputDate = Calendar.getInstance().apply { time = date }
    inputDate.set(Calendar.HOUR_OF_DAY, 0)
    inputDate.set(Calendar.MINUTE, 0)
    inputDate.set(Calendar.SECOND, 0)
    inputDate.set(Calendar.MILLISECOND, 0)

    if (inputDate == today) {
        return "now"
    }

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "Sun"
        Calendar.MONDAY -> "Mon"
        Calendar.TUESDAY -> "Tue"
        Calendar.WEDNESDAY -> "Wed"
        Calendar.THURSDAY -> "Thu"
        Calendar.FRIDAY -> "Fri"
        Calendar.SATURDAY -> "Sat"
        else -> "Invalid Day"
    }
}

