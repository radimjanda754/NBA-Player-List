package net.rjanda.casestudy.nba.network

import net.rjanda.casestudy.nba.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add authorization header API key
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(AUTHORIZATION_HEADER, BuildConfig.API_KEY)

        return chain.proceed(requestBuilder.build())
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

}