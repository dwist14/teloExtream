package com.telo.core.di.module

import android.app.Application
import android.content.res.Resources
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.internal.bind.DateTypeAdapter
import com.telo.core.util.AppSchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder()
            .registerTypeAdapter(Int::class.java, JsonDeserializer { json, _, _ ->
                try {
                    return@JsonDeserializer json.asInt
                } catch (e: NumberFormatException) {
                    return@JsonDeserializer 0
                }
            })
            .registerTypeAdapter(Date::class.java, DateTypeAdapter())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

    @Provides
    @Singleton
    fun providesApplication() = application

    @Provides
    @Singleton
    fun providesResources(): Resources = application.resources

//    @Provides
//    @Singleton
//    fun providesSessionManager() = SessionManager(application)

    @Provides
    @Singleton
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    @Singleton
    fun provideSchedulerProvider() = AppSchedulerProvider()

    @Provides
    @Singleton
    fun provideFirebaseJobDispatcher() : FirebaseJobDispatcher =
            FirebaseJobDispatcher(GooglePlayDriver(application))

//    @Provides
//    fun provideCrashlytics(): Crashlytics = Crashlytics()

}