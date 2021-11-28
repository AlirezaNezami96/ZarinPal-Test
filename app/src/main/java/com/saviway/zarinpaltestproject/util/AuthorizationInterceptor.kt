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
