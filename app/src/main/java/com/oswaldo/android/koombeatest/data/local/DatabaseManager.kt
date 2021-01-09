package com.oswaldo.android.koombeatest.data.local

import android.content.Context
import android.util.Log
import com.couchbase.lite.*


class DatabaseManager {
    var currentUser: String? = null
    private val dbName = "posts"
    private var listenerToken: ListenerToken? = null

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

    fun initCouchbaseLite(context: Context?) {
        CouchbaseLite.init(context!!)
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
            registerForDatabaseChanges()
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    private fun registerForDatabaseChanges(){
        // Add database change listener
        listenerToken = database!!.addChangeListener { change ->
            if (change != null) {
                for (docId in change.documentIDs) {
                    val doc: Document? = database!!.getDocument(docId!!)
                    if (doc != null) {
                        Log.i("DatabaseChangeEvent", "Document was added/updated")
                    } else {
                        Log.i("DatabaseChangeEvent", "Document was deleted")
                    }
                }
            }
        }
    }

    fun closeDatabaseForUser() {
        try {
            if (database != null) {
                deregisterForDatabaseChanges()
                database!!.close()
                database = null
            }
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    private fun deregisterForDatabaseChanges(){
        if (listenerToken != null) {
            database!!.removeChangeListener(listenerToken!!)
        }
    }
}