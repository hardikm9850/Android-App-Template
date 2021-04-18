package com.hardik.myapplication.network

import retrofit2.http.GET
import retrofit2.http.Path

interface AppleSongsApi {
    @GET("WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit={limit}/xml")
    suspend fun getTopSongs(@Path("limit") limit: String): Unit
}