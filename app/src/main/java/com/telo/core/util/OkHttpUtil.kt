package com.telo.core.util

import android.content.Context
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class OkHttpUtil {

//    @Inject
//    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var context: Context

    private fun getOkHttpClient(): OkHttpClient {
//        val headerInterceptor = HeaderInterceptor(sessionManager, context)
        return OkHttpClient.Builder()
//                .addInterceptor(headerInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    fun getSingleGetRequest(url: String): Single<String> {
        return Single.create(SingleOnSubscribe<String> { e ->
            val request = getRequest()
                    .url(url)
                    .build()
            val response = getOkHttpClient().newCall(request).execute()
            e.onSuccess(response.body()?.string()!!)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getSinglePostRequest(url: String, jsonObject: JSONObject): Single<String> {
        return Single.create(SingleOnSubscribe<String> { e ->
            val formBody = FormBody.Builder()
            val iterator = jsonObject.keys()
            while (iterator.hasNext()) {
                val key = iterator.next()
                formBody.add(key, jsonObject.getString(key))
            }
            val requestBody = formBody.build()
            val request = getRequest()
                    .url(url)
                    .post(requestBody)
                    .build()
            val response = getOkHttpClient().newCall(request).execute()
            e.onSuccess(response.body()?.string()!!)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getRequest(): Request.Builder = Request.Builder()
}