package com.telo.core.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author panicDev
 * @created 19/04/18.
 * @project pergikulinerAndroid.
 */
@Module
class ContextModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

}