package com.telo.core.di.module

import android.app.Application
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.telo.core.util.HeaderInterceptor
import com.telo.app.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class OkHttpModule {
    private fun getBaseBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
    }

    @Provides
    @Singleton
    fun providesOkHttpCache(pApplication: Application): Cache =
            Cache(pApplication.cacheDir, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun providesHeaderInterceptor(application: Application): HeaderInterceptor =
            HeaderInterceptor(application)

    @Provides
    @Singleton
    fun providesOkHttp(headerInterceptor: HeaderInterceptor): OkHttpClient = getBaseBuilder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BODY)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .build())
            .build()
}
