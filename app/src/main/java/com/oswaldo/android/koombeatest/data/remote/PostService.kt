package com.oswaldo.android.koombeatest.data.remote

import com.oswaldo.android.koombeatest.data.models.PostResponse
import com.oswaldo.android.koombeatest.utils.Constants.URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiClient {

    private var servicesApiInterface: PostService? = null

    fun build(): PostService {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            PostService::class.java)

        return servicesApiInterface as PostService
    }


    interface PostService {
        @GET("posts")
        suspend fun getPosts(): Response<PostResponse>
    }
}
