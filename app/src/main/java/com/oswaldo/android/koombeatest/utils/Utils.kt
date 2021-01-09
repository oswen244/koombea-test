package com.oswaldo.android.koombeatest.utils

import com.couchbase.lite.Document
import com.couchbase.lite.MutableDocument
import com.google.gson.Gson
import com.oswaldo.android.koombeatest.KApplication
import com.oswaldo.android.koombeatest.data.local.DatabaseManager
import com.oswaldo.android.koombeatest.data.models.PostResponse
import com.oswaldo.android.koombeatest.data.models.User


object Utils {
    fun saveLocalData(data: String){
        val databaseManager = DatabaseManager.getSharedInstance()
        databaseManager?.openOrCreateDatabaseForUser(KApplication.applicationContext(), "db")

        val map: HashMap<String, Any> = HashMap<String, Any>()
        map["data"] = data
        databaseManager?.saveDocument(map)
        /*val dataMap: HashMap<String, Any> = HashMap()
        var userMap: HashMap<String, Any>
        var postMap: HashMap<String, Any>
        data?.forEach { user ->
            userMap = HashMap()
            postMap = HashMap()
            userMap["uid"] = user.uid
            userMap["name"] = user.name
            userMap["email"] = user.email!!
            userMap["profilePic"] = user.profile_pic
            postMap["id"] = user.post.id
            postMap["date"] = user.post.date
            postMap["pics"] = user.post.pics
            userMap["posts"] = postMap

            dataMap["$user.uid"] = userMap
        }
        map["data"] = dataMap
        databaseManager?.saveDocument(map)*/

    }

    fun getLocalDocument(): PostResponse?{
        val databaseManager = DatabaseManager.getSharedInstance()
        databaseManager?.openOrCreateDatabaseForUser(KApplication.applicationContext(), "db")
        val document: Document? = databaseManager?.fetchDocument("0")
        var response: PostResponse? = null
        document?.let {
            response = Gson().fromJson(it.getString("data"), PostResponse::class.java)
        }

        return response!!
    }
}