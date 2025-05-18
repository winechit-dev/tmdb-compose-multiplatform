package com.wcp.tmdbcmp.data

import com.wcp.tmdbcmp.data.database.dbFileName


fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/$dbFileName"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
    )
}