package com.telo.core.di.module

import com.telo.core.component.EndPoints
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by panicLabs
 * on 14/01/18.
 */
@Module
class ApiModule {
    @Provides
    @Singleton
    fun providesEndpoints(retrofit: Retrofit): EndPoints = retrofit.create(EndPoints::class.java)
}