package com.arctouch.codechallenge.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.ParameterizedType

open class BaseRepository<E>(private val apiService: ApiService) {

    private fun <E> getApi(api: Class<E>): E {
        return apiService[api]
    }

    protected fun getApi(): E {
        return getApi(apiClass())
    }

    //configure schedulers
    protected fun <T> schedule(single: Single<T>): Single<T> {
        return single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    @Suppress("UNCHECKED_CAST")
    private fun apiClass(): Class<E> {
        val clazz: Class<out BaseRepository<*>> = javaClass
        val types = (clazz.genericSuperclass as ParameterizedType).actualTypeArguments
        return types[0] as Class<E>
    }
}