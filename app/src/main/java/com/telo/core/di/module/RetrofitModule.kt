package com.telo.core.di.module

import com.google.gson.Gson
import com.telo.app.BuildConfig
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
            GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideNullOrEmptyConverterFactory(): Converter.Factory =
            object : Converter.Factory() {
                override fun responseBodyConverter(
                        type: Type,
                        annotations: Array<out Annotation>,
                        retrofit: Retrofit
                ): Converter<ResponseBody, Any?> {
                    val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(
                            this,
                            type,
                            annotations
                    )

                    return Converter { body: ResponseBody ->
                        if (body.contentLength() == 0L) null
                        else nextResponseBodyConverter.convert(body)
                    }
                }
            }

    @Provides
    @Singleton
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())


    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient,
                         rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
                         nullOrEmptyConverterFactory: Converter.Factory,
                         gsonConverterFactory: GsonConverterFactory):
            Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .addConverterFactory(nullOrEmptyConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
}