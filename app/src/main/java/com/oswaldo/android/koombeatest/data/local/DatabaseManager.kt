package com.oswaldo.android.koombeatest.data.local

import android.content.Context
import android.util.Log
import com.couchbase.lite.*


class DatabaseManager {
    var currentUser: String? = null
    private val dbName = "posts"

    companion object{
        private var instance: DatabaseManager? = null
        private var database: Database? = null

        fun getSharedInstance(): DatabaseManager? {
            if (instance == null) {
                instance = DatabaseManager()
            }
            return instance
        }

        fun getDatabase(): Database? {
            return database
        }
    }

    fun fetchDocument(id: String): Document{
        val document = database?.getDocument(id)!!
        closeDatabaseForUser()
        return document
    }

    fun saveDocument(data: HashMap<String, Any>){
        val mutableDocument = MutableDocument("0", data)
        database?.save(mutableDocument)
        closeDatabaseForUser()
    }

    fun openOrCreateDatabaseForUser(context: Context, username: String) {
        val config = DatabaseConfiguration()
        config.directory = String.format("%s/%s", context.filesDir, username)
        currentUser = username
        try {
            database = Database(dbName, config)
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    private fun closeDatabaseForUser() {
        try {
            if (database != null) {
                database!!.close()
                database = null
            }
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }
}