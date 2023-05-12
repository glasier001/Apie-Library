package com.example.apielib.utils

import com.example.apielib.APIEPackage
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Streaming
import retrofit2.http.Url


object RetrofitBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })
                .build()
        )
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

interface ApieService {
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>

}
