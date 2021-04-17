package com.remyrobotics.graphql

import com.apollographql.apollo.api.Response
import com.remyrobotics.graphql.helper.ApolloHttp
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

/**
 *  Unit test for QraphQL Response
 */
class GraphQLUnitTest {

    private val latch: CountDownLatch = CountDownLatch(1)

    @Test
    fun responseIsNotNull() {
        var error: String? = null
        ApolloHttp.queryLocations(1, "") { res, err ->
            latch.countDown()
            error = err
        }
        latch.await()
        assertEquals(null, error)
    }

    @Test
    fun locationsSizeInFirstPage() {
        var response: Response<GetLocaltionsQuery.Data>? = null
        ApolloHttp.queryLocations(1, "") { res, err ->
            latch.countDown()
            response = res
        }
        latch.await()
        assertEquals(20, response?.data?.locations?.results?.size)
    }

    @Test
    fun residentsSizeInFirstLocation() {
        var response: Response<GetLocaltionsQuery.Data>? = null
        ApolloHttp.queryLocations(1, "") { res, err ->
            latch.countDown()
            response = res
        }
        latch.await()
        assertEquals(27, response?.data?.locations?.results?.get(0)?.residents?.size)
    }

    @Test
    fun locationsSizeWithFilterRick() {
        var response: Response<GetLocaltionsQuery.Data>? = null
        ApolloHttp.queryLocations(1, "rick") { res, err ->
            latch.countDown()
            response = res
        }
        latch.await()
        assertEquals(4, response?.data?.locations?.results?.size)
    }

    @Test
    fun locationsSizeWithFilterAnything() {
        var response: Response<GetLocaltionsQuery.Data>? = null
        ApolloHttp.queryLocations(1, "remyrobotics") { res, err ->
            latch.countDown()
            response = res
        }
        latch.await()
        assertEquals(null, response?.data?.locations?.results?.size)
    }
}