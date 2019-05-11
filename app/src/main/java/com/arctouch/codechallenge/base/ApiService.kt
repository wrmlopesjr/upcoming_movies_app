package com.arctouch.codechallenge.base

import com.arctouch.codechallenge.BASE_URL
import com.arctouch.codechallenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

open class ApiService {

    private var retrofit: Retrofit? = null
    private val services = HashMap<Class<*>, Any?>()
    private var httpClient: OkHttpClient? = null

    private fun getRetrofit(): Retrofit {
        return retrofit ?: let {

            retrofit = Retrofit.Builder()
                    .client(getHttpClient())
                    .baseUrl(getBaseURL())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            return retrofit as Retrofit
        }
    }

    protected open fun getBaseURL() = BASE_URL

    private fun getHttpClient(): OkHttpClient {
        return httpClient ?: let {
            httpClient = getBuilder().build()
            return httpClient as OkHttpClient
        }
    }

    protected open fun getBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        return builder
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(endpointClass: Class<T>): T {
        var endpoint: Any? = services[endpointClass]
        if (endpoint == null) {
            endpoint = getRetrofit().create(endpointClass)
            services[endpointClass] = endpoint
        }
        return endpoint as T
    }
}