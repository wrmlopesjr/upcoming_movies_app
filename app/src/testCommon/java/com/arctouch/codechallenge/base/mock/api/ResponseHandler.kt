package com.arctouch.codechallenge.base.mock.api

import okhttp3.Request

interface ResponseHandler {
    fun getResponse(request: Request, path: String): String
}
