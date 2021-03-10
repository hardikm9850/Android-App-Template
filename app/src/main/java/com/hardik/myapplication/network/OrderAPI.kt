package com.hardik.myapplication.network

import com.hardik.myapplication.network.beans.OrderResponse
import retrofit2.http.GET

interface OrderAPI {
    @GET("v2/5c8b7873360000881d8f80ed")
    suspend fun getOrders(): OrderResponse
}