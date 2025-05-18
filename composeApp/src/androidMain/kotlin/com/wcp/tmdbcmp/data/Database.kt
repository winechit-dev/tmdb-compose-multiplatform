package com.wcp.tmdbcmp.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wcp.tmdbcmp.data.database.AppDatabase
import com.wcp.tmdbcmp.data.database.dbFileName

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(dbFileName)
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}