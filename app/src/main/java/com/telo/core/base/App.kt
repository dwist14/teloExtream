package com.telo.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp
import com.telo.core.component.AppComponent
import com.telo.core.component.DaggerAppComponent
import com.telo.core.di.module.AppModule
import io.reactivex.plugins.RxJavaPlugins
import org.greenrobot.eventbus.EventBus

@SuppressLint("StaticFieldLeak")
class App : MultiDexApplication() {

//    @Inject
//    lateinit var sessionManager: SessionManager

//    @Inject
//    lateinit var crashlytics: Crashlytics

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        initDagger()
        setupRxJavaErrorHandler()

        // EventBus Config
        BUS = EventBus.getDefault()
        AppEventsLogger.activateApp(this)

        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            Log.i(TAG, Log.getStackTraceString(e))
        }

    }

    private fun setupRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler { e ->
            e.printStackTrace()
//            Crashlytics.logException(e)
        }
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {

        private val TAG = App::class.java.simpleName

        @JvmStatic lateinit var appComponent: AppComponent

        lateinit var BUS: EventBus

        lateinit var context: Context
        fun get() = context as App

        lateinit var instance: App private set
    }
}