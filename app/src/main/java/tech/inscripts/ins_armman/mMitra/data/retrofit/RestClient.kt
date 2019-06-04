package tech.inscripts.ins_armman.mMitra.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class RestClient {
    var retrofit: Retrofit?=null
    fun getClient() : Retrofit?{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client : OkHttpClient.Builder
        return retrofit
    }
}