package com.arctouch.codechallenge.base

import android.os.Build
import com.arctouch.codechallenge.BASE_URL
import com.arctouch.codechallenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.X509TrustManager

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

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            configureTLS12(builder)
        }

        return builder
    }

    // we need to do this because themoviedb only support TLS 1.2 and it's not enabled by
    // default in android 19  (this should be fixed, right now it's accepting any certificate)
    private fun configureTLS12(builder: OkHttpClient.Builder) {
        val trustManager = object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                try {
                    chain[0].checkValidity()
                } catch (e: Exception) {
                    throw CertificateException("Certificate not valid or trusted.")
                }
            }

            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                try {
                    chain[0].checkValidity()
                } catch (e: Exception) {
                    throw CertificateException("Certificate not valid or trusted.")
                }
            }
        }

        builder.sslSocketFactory(TLSSocketFactory(), trustManager)
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