package com.oswaldo.android.koombeatest.data.repository

import android.util.Log
import com.google.gson.Gson
import com.oswaldo.android.koombeatest.KApplication
import com.oswaldo.android.koombeatest.data.OperationResult
import com.oswaldo.android.koombeatest.data.bl.IPostsRetrieve
import com.oswaldo.android.koombeatest.data.models.PostResponse
import com.oswaldo.android.koombeatest.data.models.User
import com.oswaldo.android.koombeatest.data.remote.ApiClient
import com.oswaldo.android.koombeatest.utils.NetManager
import com.oswaldo.android.koombeatest.utils.Utils
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class PostsRepository: IPostsRetrieve {

    override suspend fun getPosts(): OperationResult<PostResponse> {
        val isNetWorkAvailable = NetManager(KApplication.applicationContext())
        if(isNetWorkAvailable.isConnectedToInternet!!){
            try {
                val result = ApiClient.build().getPosts()
                result.let {
                    return if (it.isSuccessful && it.body() != null) {
                        val data = it.body()
                        Utils.saveLocalData(Gson().toJson(data))
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

        }else{
            val local = Utils.getLocalDocument()
            local?.let {
                return OperationResult.Success(local)
            }?: run {
                Log.e("ExceptionRepo", "Error reading database")
                return OperationResult.Error(Exception("Error reading database"))
            }
        }
    }
}