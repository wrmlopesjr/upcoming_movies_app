package com.arctouch.codechallenge.base

import com.squareup.moshi.Moshi

object JsonUtils {

    private var moshi: Moshi? = null

    fun getMoshi(): Moshi {
        return moshi ?: let {
            moshi = Moshi.Builder().build()
            return moshi!!
        }
    }

    inline fun <reified T> toJson(obj: T): String {
        return getMoshi().adapter(T::class.java).toJson(obj)
    }
}
