package com.arctouch.codechallenge.base

import com.arctouch.codechallenge.base.mock.MockedApiService
import com.arctouch.codechallenge.base.mock.api.ApiMock
import com.arctouch.codechallenge.data.Cache
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.declare

object TestSuite : KoinTest {

    var apiService: MockedApiService? = null
        private set

    fun mock(url: String): ApiMock {
        return ApiMock(url, apiService)
    }

    fun clearApiMocks() {
        apiService!!.clearMocks()
    }

    //set the MockedApiService with Koin
    private fun initMockedApiService() {
        apiService = MockedApiService()

        declare {
            single { apiService as ApiService }
        }
    }

    //check if the test is Instrumented or Unit, in Unit tests we set RxJava to run synchronously
    fun init(instrumented: Boolean) {
        GlobalContext.getOrNull() ?: startKoin { modules(appComponent) }

        if (!instrumented) RxTestScheduler.init()

        initMockedApiService()
    }

    fun clear() {
        clearApiMocks()
        clearCaches()
        stopKoin()
    }

    private fun clearCaches() {
        Cache.clear()
    }

}
