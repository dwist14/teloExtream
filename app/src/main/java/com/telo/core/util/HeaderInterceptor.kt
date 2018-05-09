package com.telo.core.util

import android.content.Context
import com.telo.app.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request : Request = chain.request()
        val builder : Request.Builder

        builder = request.newBuilder()

        builder.addHeader(Constants.ACCEPT_TYPE, Constants.ACCEPT_KEY)
//        builder.addHeader(context.getString(R.string.auth_type), context.getString(R.string.auth_key))

        val newRequest : Request = builder.build()
        return chain.proceed(newRequest)
    }


}