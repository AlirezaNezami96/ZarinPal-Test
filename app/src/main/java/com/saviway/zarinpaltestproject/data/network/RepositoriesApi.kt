package com.saviway.zarinpaltestproject.data.network

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.exception.ApolloException
import com.saviway.zarinpaltestproject.ProfileQuery
import com.saviway.zarinpaltestproject.RepositoriesQuery
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class RepositoriesApi
@Inject constructor(
    private val client: ApolloClient
) {

    /**
     * Using 'suspendCoroutine' for returning data from inside of
     * coroutine scope
     * Used `httpCachePolicy` for get from cache if there was any response saved in
     * http cache
     */
    suspend fun getRepositories(): RepositoriesQuery.Data? {
        return suspendCoroutine { continuation ->
            client.query(RepositoriesQuery())
                .httpCachePolicy(HttpCachePolicy.CACHE_FIRST.expireAfter(10,TimeUnit.MINUTES))
                .enqueue(object :
                ApolloCall.Callback<RepositoriesQuery.Data>() {
                override fun onResponse(response: Response<RepositoriesQuery.Data>) {
                    continuation.resume(response.data)
                }
                override fun onFailure(e: ApolloException) {
                    continuation.resume(null)
                }
            })
        }
    }
}