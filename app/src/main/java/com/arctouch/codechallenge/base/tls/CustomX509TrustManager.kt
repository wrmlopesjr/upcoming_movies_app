package com.arctouch.codechallenge.base.tls

import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class CustomX509TrustManager : X509TrustManager {

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