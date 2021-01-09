package com.oswaldo.android.koombeatest.utils

import android.view.View
import com.couchbase.lite.Document
import com.couchbase.lite.MutableDocument
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.oswaldo.android.koombeatest.KApplication
import com.oswaldo.android.koombeatest.data.local.DatabaseManager
import com.oswaldo.android.koombeatest.data.models.PostResponse
import com.oswaldo.android.koombeatest.data.models.User
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


object Utils {
    fun saveLocalData(data: String){
        val databaseManager = DatabaseManager.getSharedInstance()
        databaseManager?.openOrCreateDatabaseForUser(KApplication.applicationContext(), "db")

        val map: HashMap<String, Any> = HashMap<String, Any>()
        map["data"] = data
        databaseManager?.saveDocument(map)
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

    fun shimmerStart(shimmer: ShimmerFrameLayout){
        shimmer.startShimmerAnimation()
        shimmer.visibility = View.VISIBLE
    }

    fun shimmerStop(shimmer: ShimmerFrameLayout){
        shimmer.stopShimmerAnimation()
        shimmer.visibility = View.GONE
    }

    fun parseDate(
        inputDateString: String?,
        inputFormat: String,
        outputFormat: String
    ): String? {
        val inputDateFormat = SimpleDateFormat(inputFormat, Locale.US)
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.US)
        var date: Date? = null
        var outputDateString: String? = null
        try {
            date = inputDateFormat.parse(inputDateString)
            outputDateString = outputDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateString
    }
}