package com.hardik.myapplication.util.extension

import android.net.ConnectivityManager

fun ConnectivityManager.isConnected() = this.activeNetworkInfo != null
      && this.activeNetworkInfo!!.isConnected
