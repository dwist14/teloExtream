package com.telo.core.component

import android.app.Application
import android.content.res.Resources
import com.telo.core.di.module.ApiModule
import com.google.android.gms.analytics.Tracker
import com.google.gson.Gson
import com.telo.core.util.AppSchedulerProvider
import com.telo.core.di.module.AppModule
import com.telo.core.di.module.LoggingModule
import com.telo.core.di.module.OkHttpModule
import com.telo.core.di.module.RetrofitModule
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (RetrofitModule::class),
    (ApiModule::class),
    (OkHttpModule::class),
    (LoggingModule::class)]
)

interface AppComponent {
    fun application(): Application
    fun gson(): Gson
    fun resources(): Resources
    fun retrofit(): Retrofit
    fun endpoints(): EndPoints
    fun cache(): Cache
    fun client(): OkHttpClient
    fun compositeDisposable(): CompositeDisposable
    fun schedulerProvider(): AppSchedulerProvider
//        fun sessionManager(): SessionManager
//    fun crashlytics(): Crashlytics

}