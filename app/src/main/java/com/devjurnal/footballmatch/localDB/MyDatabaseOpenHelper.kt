package com.devjurnal.footballmatch.localDB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
// Here you create tables
        db.createTable("TABLE_FAVORITE", true,
                FavoriteContract.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteContract.ID_EVENT to TEXT + UNIQUE,
                FavoriteContract.DATE_EVENT to TEXT,
                FavoriteContract.HOME_TEAM to TEXT,
                FavoriteContract.AWAY_TEAM to TEXT,
                FavoriteContract.HOME_ID to TEXT,
                FavoriteContract.AWAY_ID to TEXT,
                FavoriteContract.TEAM_HOME_SCORE to TEXT,
                FavoriteContract.TEAM_AWAY_SCORE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("TABLE_FAVORITE", true)
    }
}


// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)