package com.arctouch.codechallenge.base.mock

import android.util.Log
import com.arctouch.codechallenge.base.ApiService
import com.arctouch.codechallenge.base.FileUtils
import com.arctouch.codechallenge.base.mock.api.ApiMock
import okhttp3.*
import java.util.*

class MockedApiService : ApiService() {

    private val JsonMediaType = MediaType.parse("application/json")
    private val mockedApis = HashMap<String, ApiMock>()

    //we override the get builder method and use an interceptor
    //so instead of getting the data from the api it's going to load the data from JSON files
    override fun getBuilder(): OkHttpClient.Builder {
        val builder = super.getBuilder()
        builder.addInterceptor(mockInterceptor())
        return builder
    }

    override fun getBaseURL() = "http://MOCKED.net"

    private fun mockInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val path = getPath(request)

            val api = request.method().toUpperCase() + " " + path
            var mock: ApiMock? = mockedApis[api]
            if (mock == null) {
                mock = mockedApis[path]
            }
            if (mock != null) {
                return@Interceptor customResponse(chain, mock)
            }

            defaultResponse(chain, path)
        }
    }

    private fun getPath(request: Request): String {
        var path = request.url().encodedPath()
        if ("/" == path) {
            path = request.url().toString()
        }
        return path
    }

    //custom responses are dynamic mocks, you can create it in the test code
    private fun customResponse(chain: Interceptor.Chain, mock: ApiMock): Response {
        val builder = defaultBuilder(chain)

        if (mock.getCode() == ApiMock.FORCED_MOCK_EXCEPTION_CODE) {
            throw RuntimeException("ApiMock test exception")
        }
        return mock.error?.let { error ->
            builder.code(mock.getCode())
                    .body(error.response().errorBody())
                    .build()
        } ?: builder.code(mock.getCode())
                .body(ResponseBody.create(JsonMediaType, mock.getResponse(chain.request())))
                .build()
    }

    //default response is getting the response from JSON files
    private fun defaultResponse(chain: Interceptor.Chain, api: String): Response {
        val builder = defaultBuilder(chain)

        val content = FileUtils.readJson(api.substring(1) + ".json") ?: return apiNotMocked(api)
        return builder.code(200)
                .body(ResponseBody.create(JsonMediaType, content))
                .build()
    }

    private fun defaultBuilder(chain: Interceptor.Chain): Response.Builder {
        val builder = Response.Builder()
        return builder.message("")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
    }

    private fun apiNotMocked(api: String): Response {
        Log.e("MockedApiService", "api not mocked -> $api")
        throw RuntimeException("api not mocked -> $api")
    }

    fun addMockedApi(url: String, mock: ApiMock) {
        mockedApis[url] = mock
    }

    fun clearMocks() {
        mockedApis.clear()
    }
}