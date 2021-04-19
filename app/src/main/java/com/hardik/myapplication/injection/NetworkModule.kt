package com.hardik.myapplication.injection

import com.hardik.myapplication.network.AppleSongsApi
import com.hardik.myapplication.network.interceptor.NoConnectivityInterceptor
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(noConnectivityInterceptor: NoConnectivityInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(noConnectivityInterceptor)
        builder.hostnameVerifier { _, _ -> true }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
            baseUrl: String,
            httpClient: OkHttpClient,
            xmlParserFactory: TikXmlConverterFactory
    ): Retrofit {

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(xmlParserFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideXmlParser(): TikXmlConverterFactory {
        return TikXmlConverterFactory.create(
                TikXml.Builder()
                        .exceptionOnUnreadXml(false)
                        .build()
        )
    }

    @Provides
    @Singleton
    fun provideOrderAPI(retrofit: Retrofit): AppleSongsApi =
            retrofit.create(AppleSongsApi::class.java)

    @Provides
    @Singleton
    fun provideBaseUrl() = "http://ax.itunes.apple.com/"
}
