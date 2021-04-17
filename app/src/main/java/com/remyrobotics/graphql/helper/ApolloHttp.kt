package com.remyrobotics.graphql.helper

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.remyrobotics.graphql.GetLocaltionsQuery
import okhttp3.OkHttpClient

object ApolloHttp {

    val BASE_URL = "https://rickandmortyapi.com/graphql"

    fun apolloClient(): ApolloClient {

        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    fun queryLocations(page: Int, keyword: String, completionHandler: (res: Response<GetLocaltionsQuery.Data>?, err: String?)-> Unit){
        apolloClient().query(GetLocaltionsQuery(page = page, search = keyword))
            .enqueue(object : ApolloCall.Callback<GetLocaltionsQuery.Data>() {
                override fun onResponse(response: Response<GetLocaltionsQuery.Data>) {
                    completionHandler(response, null)
                }

                override fun onFailure(e: ApolloException) {
                    completionHandler(null, e.toString())
                }
            })
    }
}