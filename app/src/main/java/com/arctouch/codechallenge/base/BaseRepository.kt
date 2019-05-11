package com.arctouch.codechallenge.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.ParameterizedType

open class BaseRepository<E>(private val endpointService: ApiService) {

    private fun <E> getEndpoint(endpoint: Class<E>): E {
        return endpointService[endpoint]
    }

    protected fun getEndpoint(): E {
        return getEndpoint(endpointClass())
    }

    //configure schedulers
    protected fun <T> schedule(single: Single<T>): Single<T> {
        return single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    @Suppress("UNCHECKED_CAST")
    private fun endpointClass(): Class<E> {
        val clazz: Class<out BaseRepository<*>> = javaClass
        val types = (clazz.genericSuperclass as ParameterizedType).actualTypeArguments
        return types[0] as Class<E>
    }
}