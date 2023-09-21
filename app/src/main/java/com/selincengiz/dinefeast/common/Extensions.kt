package com.selincengiz.dinefeast.common

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

import java.util.Calendar

object Extensions {
    fun ImageView.loadUrl(url: String?) {

        Glide.with(this.context).load(url).circleCrop().into(this)

    }

}