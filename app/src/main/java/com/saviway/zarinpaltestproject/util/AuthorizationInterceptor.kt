package com.saviway.zarinpaltestproject.util

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "bearer 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")
            .build()

        return chain.proceed(request)
    }
}