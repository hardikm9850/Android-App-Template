package com.hardik.myapplication.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hardik.myapplication.network.AppleSongsApi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideGson(): Gson {
    return GsonBuilder()
      .create()
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
      .addInterceptor(HttpLoggingInterceptor())
    return builder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(baseUrl : String, httpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(httpClient)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()
  }

  @Provides
  @Singleton
  fun provideOrderAPI(retrofit: Retrofit): AppleSongsApi = retrofit.create(AppleSongsApi::class.java)

  @Provides
  @Singleton
  fun provideBaseUrl()  = "https://http://ax.itunes.apple.com/"
}
