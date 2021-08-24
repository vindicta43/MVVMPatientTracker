package com.alperen.mvvmpatienttracker.utils

import android.app.AlertDialog
import android.content.Context

class AlertMaker(private val context: Context?) {
    fun makeAlert(msg: String?) {
        AlertDialog.Builder(context)
            .setMessage(msg)
            .setCancelable(true)
            .setPositiveButton("Tamam") { _, _ ->}
            .show()
    }
}