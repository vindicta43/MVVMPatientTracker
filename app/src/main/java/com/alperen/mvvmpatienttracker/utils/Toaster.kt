package com.alperen.mvvmpatienttracker.utils

import android.content.Context
import android.widget.Toast

class Toaster(private val context: Context?) {
    fun makeToast(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}