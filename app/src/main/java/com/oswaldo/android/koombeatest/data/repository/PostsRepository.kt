package com.oswaldo.android.koombeatest.data.repository

import android.util.Log
import com.oswaldo.android.koombeatest.data.OperationResult
import com.oswaldo.android.koombeatest.data.bl.IPostsRetrieve
import com.oswaldo.android.koombeatest.data.models.PostResponse
import com.oswaldo.android.koombeatest.data.remote.ApiClient
import java.lang.Exception

class PostsRepository: IPostsRetrieve {

    override suspend fun getPosts(): OperationResult<PostResponse> {

        //TODO: insert local retrieve
        try {
            val result = ApiClient.build().getPosts()
            result.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    OperationResult.Success(data)
                } else {
                    val message = it.body()?.statusMessage
                    OperationResult.Error(Exception(message))
                }
            }
        }catch (e: Exception){
            Log.e("ExceptionRepo", e.message)
            return OperationResult.Error(e)
        }
    }
}